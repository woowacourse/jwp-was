package webserver.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import webserver.WebServer;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

class CreateUserControllerTest {
    private WebTestClient webTestClient;
    private Thread thread;

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
        thread = new Thread(() -> WebServer.main(new String[0]));
        thread.start();
    }

    @Test
    public void createUser() {
        URI location = webTestClient.post()
                .uri("/user/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("userId", "userId")
                        .with("password", "password")
                        .with("name", "name")
                        .with("email", "email@email.com"))
                .exchange()
                .expectStatus().isFound()
                .expectBody()
                .returnResult()
                .getResponseHeaders().getLocation();

        assertThat(String.valueOf(location)).isEqualTo("/index.html");
    }

    @AfterEach
    public void tearDown() {
        thread.interrupt();
    }
}