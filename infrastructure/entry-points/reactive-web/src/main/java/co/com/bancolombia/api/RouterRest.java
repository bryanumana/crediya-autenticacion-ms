package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springdoc.core.annotations.RouterOperation;


import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.awt.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    @RouterOperation (
            path = "/api/v1/usuarios",
            method = RequestMethod.POST,
            beanClass = Handler.class,
            beanMethod = "save",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public RouterFunction<ServerResponse> userRoutes(Handler handler) {
        return route(POST("/api/v1/usuarios"), handler::save);
    }
}
