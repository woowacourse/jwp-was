package controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import webserver.HttpResponse;
import webserver.HttpStatus;
import webserver.RequestParser;
import webserver.WebServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginControllerTest {

    private WebTestClient webTestClient;
    private Thread serverThread;

    @BeforeEach
    void setup() {
        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
        serverThread = new Thread(() -> WebServer.main(new String[0]));
        serverThread.start();
        webTestClient.post()
            .uri(SignUpController.USER_CREATE_URL)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData("userId", "john123")
                .with("password", "p@ssW0rd")
                .with("name", "john")
                .with("email", "john@example.com"))
            .exchange()
            .expectStatus().is3xxRedirection()
            .expectHeader().valueMatches("Location", "/index.html")
            .expectBody().returnResult();
    }

    @AfterEach
    void cleanup() {
        serverThread.interrupt();
    }

    @Test
    void login() {
        webTestClient.post()
            .uri(LoginController.USER_LOGIN_URL)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData("userId", "john123")
                .with("password", "p@ssW0rd"))
            .exchange()
            .expectStatus().is3xxRedirection()
            .expectHeader().valueMatches("Location", "/index.html");
    }

    @Test
    void login_failed2() {
        webTestClient.post()
            .uri(LoginController.USER_LOGIN_URL)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData("userId", "john123")
            .with("password", "p@ssW0r"))
            .exchange()
            .expectStatus().is3xxRedirection()
            .expectHeader().valueMatches("Location", "/user/login_failed.html");
    }
}
