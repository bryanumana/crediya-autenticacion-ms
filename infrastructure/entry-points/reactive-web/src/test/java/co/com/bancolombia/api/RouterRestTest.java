package co.com.bancolombia.api;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.usecase.registreruser.RegistrerUserUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest
@Import({RouterRest.class, Handler.class})
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public RegistrerUserUseCase registrerUserUseCase() {
            RegistrerUserUseCase mock = Mockito.mock(RegistrerUserUseCase.class);

            // Configurar el comportamiento del mock para evitar NullPointerException
            User mockUser = User.builder()
                    .id("1")
                    .email("test@test.com")
                    .build();

            Mockito.when(mock.saveUser(Mockito.any()))
                    .thenReturn(Mono.just(mockUser));

            return mock;
        }
    }

    @Test
    void testListenGETUseCase() {
        webTestClient.get()
                .uri("/api/usecase/path")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(userResponse ->
                        Assertions.assertThat(userResponse).isEmpty()
                );
    }

    @Test
    void testListenGETOtherUseCase() {
        webTestClient.get()
                .uri("/api/otherusercase/path")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(userResponse ->
                        Assertions.assertThat(userResponse).isEmpty()
                );
    }

    @Test
    void testListenPOSTUseCase() {
        // JSON válido según tu UserRequestDTO
        String userJson = """
        {
            "name": "Test User",
            "email": "test@test.com"
        }
        """;

        webTestClient.post()
                .uri("/api/usecase/otherpath")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userJson)
                .exchange()
                .expectStatus().isCreated() // Cambiado a 201 (CREATED)
                .expectBody()
                .jsonPath("$.message").isEqualTo("El usuario fue creado")
                .jsonPath("$.status").isEqualTo(201);
    }
}