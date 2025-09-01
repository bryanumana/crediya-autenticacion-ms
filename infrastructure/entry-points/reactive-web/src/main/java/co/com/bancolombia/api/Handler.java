package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.MessageResponseDTO;
import co.com.bancolombia.api.dto.UserRequestDTO;
import co.com.bancolombia.api.mapper.UserMapper;
import co.com.bancolombia.usecase.registreruser.RegistrerUserUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Locale;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Handler {

    private static final Logger logger = LoggerFactory.getLogger(Handler.class);
    private final RegistrerUserUseCase registrerUserUseCase;
    private final Validator validator;
    private final MessageSource messageSource;

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(UserRequestDTO.class)
                .flatMap(userRequest -> {
                    Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(userRequest);
                    if (!violations.isEmpty()) {
                        var bindingResult = new org.springframework.validation.BeanPropertyBindingResult(userRequest, userRequest.getClass().getSimpleName());
                        violations.forEach(v ->
                                bindingResult.addError(
                                        new org.springframework.validation.FieldError(
                                                userRequest.getClass().getSimpleName(),
                                                v.getPropertyPath().toString(),
                                                v.getMessage()
                                        )
                                )
                        );
                        return Mono.error(new WebExchangeBindException(null, bindingResult));
                    }
                    return Mono.just(userRequest);
                })
                .map(UserMapper::toEntity)
                .flatMap(registrerUserUseCase::saveUser)
                .map(UserMapper::toResponse)
                .flatMap(dto -> {
                    logger.info("Usuario registrado exitosamente : {}", dto.getEmail());
                    String successMessage = messageSource.getMessage("user.created.success", null, Locale.getDefault());
                    return ServerResponse.status(HttpStatus.CREATED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(new MessageResponseDTO(successMessage));
                });
    }
}
