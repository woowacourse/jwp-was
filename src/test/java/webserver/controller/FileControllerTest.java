package webserver.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import webserver.WebServer;

class FileControllerTest {
    private WebTestClient webTestClient;
    private Thread thread;

    @BeforeEach
    public void setup() {
        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
        thread = new Thread(() -> WebServer.main(new String[0]));
        thread.start();
    }

    @Test
    public void serveFile() {
        webTestClient.get()
                .uri("/")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @AfterEach
    public void tearDown() {
        thread.interrupt();
    }
}