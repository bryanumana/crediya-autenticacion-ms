package co.com.bancolombia.usecase.registreruser;

import co.com.bancolombia.model.exceptions.ValidateException;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrerUserUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegistrerUserUseCase registrerUserUseCase;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = User.builder().id("1").name("ABC").lastName("DEF").email("alfabeto@example.com").baseSalary(5000000.0).build();
    }

    @Test
    void successfulUserSave() {
        when(userRepository.existsUserByEmail(any())).thenReturn(Mono.just(false));
        when(userRepository.saveUser(any(User.class))).thenReturn(Mono.just(validUser));

        StepVerifier.create(registrerUserUseCase.saveUser(validUser)).expectNext(validUser).verifyComplete();
    }

    @Test
    void salaryIsNegative() {
        User invalidUser = validUser.toBuilder().baseSalary(-1.0).build();

        StepVerifier.create(registrerUserUseCase.saveUser(invalidUser)).expectErrorMatches(throwable -> throwable instanceof ValidateException && throwable.getMessage().equals("El salario base debe estar entre $0 y $15.000.000")).verify();
    }

    @Test
    void salaryOverUpperLimit() {
        User invalidUser = validUser.toBuilder().baseSalary(15000000.1).build();

        StepVerifier.create(registrerUserUseCase.saveUser(invalidUser)).expectErrorMatches(throwable -> throwable instanceof ValidateException && throwable.getMessage().equals("El salario base debe estar entre $0 y $15.000.000")).verify();
    }

    @Test
    void salaryAtUpperLimit() {
        User edgeUser = validUser.toBuilder().baseSalary(15000000.0).build();

        when(userRepository.existsUserByEmail(any())).thenReturn(Mono.just(false));
        when(userRepository.saveUser(any(User.class))).thenReturn(Mono.just(edgeUser));

        StepVerifier.create(registrerUserUseCase.saveUser(edgeUser)).expectNext(edgeUser).verifyComplete();
    }

    @Test
    void emailAlreadyExists() {
        when(userRepository.existsUserByEmail(any())).thenReturn(Mono.just(true));

        StepVerifier.create(registrerUserUseCase.saveUser(validUser)).expectErrorMatches(throwable -> throwable instanceof ValidateException && throwable.getMessage().equals("El correo ya esta registrado")).verify();
    }

    @Test
    void emailValid() {
        User userWithValidEmail = validUser.toBuilder().email("qa@example.com").build();

        when(userRepository.existsUserByEmail(any())).thenReturn(Mono.just(false));
        when(userRepository.saveUser(any(User.class))).thenReturn(Mono.just(userWithValidEmail));

        StepVerifier.create(registrerUserUseCase.saveUser(userWithValidEmail)).expectNext(userWithValidEmail).verifyComplete();
    }
}
