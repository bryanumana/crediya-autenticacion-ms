package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.UserRequestDTO;
import co.com.bancolombia.api.exception.GlobalExceptionHandler;
import co.com.bancolombia.api.mapper.UserMapper;
import co.com.bancolombia.model.user.gateways.LoggerRepository;
import co.com.bancolombia.usecase.registreruser.RegistrerUserUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class Handler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final RegistrerUserUseCase registrerUserUseCase;


    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(UserRequestDTO.class)
                .map(UserMapper::toEntity)
                .flatMap(registrerUserUseCase::saveUser)
                .map(UserMapper::toResponse)
                .flatMap(dto -> {
                    logger.info("Usuario registrado: {}", dto.getEmail());
                    return ServerResponse.status(HttpStatus.CREATED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(Map.of(
                                    "status", HttpStatus.CREATED.value(),
                                    "message", "El usuario fue creado"));
                });
    }
}
