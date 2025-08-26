package co.com.bancolombia.usecase.registreruser;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;

@RequiredArgsConstructor
public class RegistrerUserUseCase {
    private final UserRepository userRepository;


    public Mono<User> saveUser(User user) {
        //General validates
        if (user.getName() == null || user.getName().isBlank()) {
            return Mono.error(new IllegalArgumentException("El primer nombre es obligatorio"));
        }
        if (user.getLastName() == null || user.getLastName().isBlank()) {
            return Mono.error(new IllegalArgumentException("El primer apellido es obligatorio"));
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return Mono.error(new IllegalArgumentException("El email es obligatorio"));
        }
        if (user.getBaseSalary() == null
                || user.getBaseSalary() < 0
                || user.getBaseSalary() > 15000000) {
            return Mono.error(new IllegalArgumentException("La salary es obligatorio y debe estar entre $0 y $15.000.000"));
        }
        //Email validate
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!Pattern.matches(emailRegex, user.getEmail())) {
            return Mono.error(new IllegalArgumentException("El email tiene un formato de correo inválido"));
        }
        return userRepository.existsUserByEmail(user.getEmail())
                .flatMap(correoExiste -> {
                    if (correoExiste) {
                        // If the email already exist, return error
                        return Mono.error(new IllegalArgumentException(
                                "El correo electrónico ya está registrado"
                        ));
                    }
                    // IOf the email doesn't exist, return the user to continue
                    return userRepository.saveUser(user);
                });
    }
}
