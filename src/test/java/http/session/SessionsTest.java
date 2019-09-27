package http.session;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import webserver.WebServer;
import webserver.support.CookieParser;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionsTest {
    private WebTestClient webTestClient;
    private Thread thread;
    private String cookie;

    static {
        DataBase.addUser(new User("userId", "password", "name", "email@gmail.com"));
    }

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
        thread = new Thread(() -> WebServer.main(new String[0]));
        thread.start();

        cookie = webTestClient.post()
                .uri("/user/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("userId", "userId")
                        .with("password", "password"))
                .exchange()
                .expectStatus().isFound()
                .expectBody()
                .returnResult()
                .getResponseHeaders().get("Set-Cookie").get(0);
    }

    @Test
    public void loginSession() {
        Map<String, String> parsedCookie = CookieParser.parse(cookie.split("; ")[0]);
        Session session = Sessions.getInstance().getSession(parsedCookie.get("JSESSIONID"));
        assertThat(session.getAttribute("logined")).isEqualTo("true");
    }

    @AfterEach
    public void tearDown() {
        thread.interrupt();
    }
}
