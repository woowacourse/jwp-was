package webserver.support;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestTest {

    @Test
    @DisplayName("Header에서 html URL 추출")
    public void extractUrl() {
        Request request = new Request("GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*");

        assertThat(request.extractUrl()).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("Header에서 루트 URL 추출")
    public void extractUrl1() {
        Request request = new Request("GET / HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*");

        assertThat(request.extractUrl()).isEqualTo("/");
    }
}