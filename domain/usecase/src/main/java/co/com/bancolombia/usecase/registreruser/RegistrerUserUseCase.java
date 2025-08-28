package co.com.bancolombia.usecase.registreruser;

import co.com.bancolombia.model.exceptions.ValidationException;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;

@RequiredArgsConstructor
public class RegistrerUserUseCase {
    private final UserRepository userRepository;
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public Mono<User> saveUser(User user) {

        if (user.getName() == null || user.getName().isBlank()) {
            return Mono.error(new ValidationException("El primer nombre es obligatorio"));
        }
        if (user.getLastName() == null || user.getLastName().isBlank()) {
            return Mono.error(new ValidationException("El primer apellido es obligatorio"));
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return Mono.error(new ValidationException("El email es obligatorio"));
        }
        if (user.getBaseSalary() == null
                || user.getBaseSalary() < 0
                || user.getBaseSalary() > 15000000) {
            return Mono.error(new ValidationException("La salario es obligatorio y debe estar entre $0 y $15.000.000"));
        }
        if (!Pattern.matches(emailRegex, user.getEmail())) {
            return Mono.error(new ValidationException("El email tiene un formato de correo inválido"));
        }

        return userRepository.existsUserByEmail(user.getEmail())
                .flatMap(existUser -> {
                    if (existUser) {
                        return Mono.error(new ValidationException("El correo electrónico ya está registrado")
                        );
                    }
                    return userRepository.saveUser(user);
                });
    }
}
