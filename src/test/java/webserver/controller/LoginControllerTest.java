package webserver.controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import webserver.WebServer;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest {
    private WebTestClient webTestClient;
    private Thread thread;

    static {
        DataBase.addUser(new User("userId", "password", "name", "email"));
    }

    @BeforeEach
    public void setup() {
        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
        thread = new Thread(() -> WebServer.main(new String[0]));
        thread.start();
    }

    @Test
    public void login() {
        URI location = webTestClient.post()
                .uri("/user/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("userId", "userId")
                        .with("password", "password"))
                .exchange()
                .expectStatus().isFound()
                .expectBody()
                .returnResult()
                .getResponseHeaders().getLocation();

        assertThat(String.valueOf(location)).isEqualTo("/index.html");
    }

    @Test
    public void loginFail() {
        URI location = webTestClient.post()
                .uri("/user/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("userId", "userId")
                        .with("password", "password1"))
                .exchange()
                .expectStatus().isFound()
                .expectBody()
                .returnResult()
                .getResponseHeaders().getLocation();

        assertThat(String.valueOf(location)).isEqualTo("/user/login_failed.html");
    }

    @AfterEach
    public void tearDown() {
        thread.interrupt();
    }
}