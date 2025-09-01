package co.com.bancolombia.usecase.registreruser;

import co.com.bancolombia.model.exceptions.ValidateException;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegistrerUserUseCase {
    private final UserRepository userRepository;

    public Mono<User> saveUser(User user) {

        if (user.getBaseSalary() < 0 || user.getBaseSalary() > 15000000) {
            return Mono.error(new ValidateException("El salario base debe estar entre $0 y $15.000.000"));
        }

        return userRepository.existsUserByEmail(user.getEmail()).flatMap(existUser -> {
            if (existUser) {
                return Mono.error(new ValidateException("El correo ya esta registrado"));
            }
            return userRepository.saveUser(user);
        });
    }
}
