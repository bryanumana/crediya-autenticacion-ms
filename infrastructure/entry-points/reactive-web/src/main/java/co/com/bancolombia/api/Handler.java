package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.ErrorResponse;
import co.com.bancolombia.api.dto.UserRequestDTO;
import co.com.bancolombia.api.dto.UserResponseDTO;
import co.com.bancolombia.api.exception.GlobalExceptionHandler;
import co.com.bancolombia.api.mapper.UserMapper;
import co.com.bancolombia.usecase.registreruser.RegistrerUserUseCase;
import io.micrometer.core.ipc.http.HttpSender;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
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


    @Operation(
            operationId = "Registrar un usuario",
            summary = "Registrar un nuevo usuario",
            description = "Crea un usuario en el sistema a partir de su nombre, apellido y correo",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserRequestDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuario registrado exitosamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error en los datos de la solicitud",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ocurrió un error inesperado en el servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )

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
