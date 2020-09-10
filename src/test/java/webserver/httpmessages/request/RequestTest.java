package webserver.httpmessages.request;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequestTest {

    private Request request;

    @BeforeEach
    void setUp() {
        String testRequest = "GET / HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n";
        request = new Request(testRequest);
    }

    @Test
    void getMethod() {
        assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    void getUri() {
        assertThat(request.getUri()).isEqualTo("/");
    }

    @Test
    void getHeaderValue() {
        assertThat(request.getHeader("Host")).isEqualTo("localhost:8080");
    }
}