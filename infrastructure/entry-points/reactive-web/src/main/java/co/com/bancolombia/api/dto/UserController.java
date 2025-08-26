package co.com.bancolombia.api.dto;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.usecase.registreruser.RegistrerUserUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = (Logger) LoggerFactory.getLogger(UserController.class);
    private final RegistrerUserUseCase registrerUserUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> registrar(@RequestBody User user) {
        log.info("Iniciando registro de usuario con correo: {}");
        return registrerUserUseCase.saveUser(user)
                .doOnSuccess(u -> log.info("Usuario registrado correctamente: {}"))
                .doOnError(e -> log.info("Error registrando usuario: {}"));}
}
