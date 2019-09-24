package controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import webserver.WebServer;

import static org.assertj.core.api.Assertions.assertThat;

public class UserListControllerTest {

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
    void logged_id() {
        webTestClient.get()
            .uri(UserListController.USER_LIST_URL)
            .cookie("logined", "true")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .consumeWith(res -> {
                assertThat(new String(res.getResponseBody()))
                    .contains("이메일");
            });
    }

    @Test
    void logged_in_failed() {
        webTestClient.get()
            .uri(UserListController.USER_LIST_URL)
            .exchange()
            .expectStatus().is3xxRedirection()
            .expectHeader().valueMatches("Location", "/user/login.html");
    }
}
