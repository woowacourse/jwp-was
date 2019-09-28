package webserver.controller;

import db.DataBase;
import http.response.Cookies;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import webserver.WebServer;
import webserver.support.CookieParser;

import static org.assertj.core.api.Assertions.assertThat;

class UserListControllerTest {
    private WebTestClient webTestClient;
    private Thread thread;
    private String jSessionId;

    static {
        DataBase.addUser(new User("testUserId", "testPassword", "testName", "testEmail"));
    }

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
        thread = new Thread(() -> WebServer.main(new String[0]));

        String cookie = webTestClient.post()
                .uri("/user/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("userId", "testUserId")
                        .with("password", "testPassword"))
                .exchange()
                .expectStatus().isFound()
                .expectBody()
                .returnResult()
                .getResponseHeaders().get("Set-Cookie").get(0);

        Cookies cookies = new Cookies(CookieParser.parse(cookie));
        jSessionId = cookies.findCookie("JSESSIONID").getValue();
    }

    @Test
    public void userList() {
        webTestClient.get()
                .uri("/user/list")
                .cookie("JSESSIONID", jSessionId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    String body = new String(response.getResponseBody());
                    assertThat(body.contains("testUserId")).isTrue();
                });
    }

    @Test
    public void userListFail() {
        webTestClient.get()
                .uri("/user/list")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    String body = new String(response.getResponseBody());
                    assertThat(body.contains("/user/login")).isTrue();
                });
    }

    @AfterEach
    public void tearDown() {
        thread.interrupt();
    }
}