package webserver.http.request;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestFactory;

class HttpRequestFactoryTest {

    @DisplayName("생성 테스트")
    @Test
    void create() throws Exception {
        String request = "POST /index.html?a=1&b=2 HTTP/1.1" + System.lineSeparator()
            + "Host: localhost:8080" + System.lineSeparator()
            + "Connection: keep-alive" + System.lineSeparator()
            + "Content-Length: 8" + System.lineSeparator()
            + "Accept: */*" + System.lineSeparator()
            + System.lineSeparator()
            + "i'm body";

        HttpRequest httpRequest = HttpRequestFactory.create(new ByteArrayInputStream(request.getBytes()));

        assertThat(httpRequest).isNotNull();
    }

    @DisplayName("body와 http method가 맞지 않는 경우 예외 처리")
    @Test
    void createWithException() throws Exception {
        String request = "GET /index.html?a=1&b=2 HTTP/1.1" + System.lineSeparator()
            + "Host: localhost:8080" + System.lineSeparator()
            + "Connection: keep-alive" + System.lineSeparator()
            + "Content-Length: 8" + System.lineSeparator()
            + "Accept: */*" + System.lineSeparator()
            + System.lineSeparator()
            + "i'm body";

        assertThatThrownBy(() -> HttpRequestFactory.create(new ByteArrayInputStream(request.getBytes())))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("허용되지 않습니다.");
    }
}