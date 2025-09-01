package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.MessageResponseDTO;
import co.com.bancolombia.api.dto.UserRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Bean
    @RouterOperation(path = "/api/v1/usuarios", method = org.springframework.web.bind.annotation.RequestMethod.POST, beanClass = Handler.class, beanMethod = "save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, operation = @Operation(operationId = "RegistrarUsuario", summary = "Registrar un nuevo usuario", description = "Crea un usuario en el sistema a partir de su nombre, apellido y correo", requestBody = @RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserRequestDTO.class))), responses = {@ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MessageResponseDTO.class))), @ApiResponse(responseCode = "400", description = "Error en los datos de la solicitud", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MessageResponseDTO.class))), @ApiResponse(responseCode = "500", description = "Ocurrió un error inesperado en el servidor", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MessageResponseDTO.class)))}))

    public RouterFunction<ServerResponse> userRoutes(Handler handler) {
        return route(POST("/api/v1/usuarios"), handler::save);
    }
}
