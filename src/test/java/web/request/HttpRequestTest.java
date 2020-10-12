package web.request;

import exception.InvalidHttpRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);
    private InputStream inputStream;

    @BeforeEach
    void setup() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GET /index.html HTTP/1.1\n");
        stringBuilder.append("Host: localhost:8080\n");
        stringBuilder.append("Connection: keep-alive\n");
        stringBuilder.append("Accept: */*\n\n");
        inputStream = new ByteArrayInputStream(stringBuilder.toString().getBytes());
    }

    @Test
    @DisplayName("HttpRequest 객체가 올바르게 생성된다")
    void createHttpRequestTest() {
        HttpRequest httpRequest = new HttpRequest(inputStream);
        Assertions.assertThat(httpRequest.getMethod().getName()).isEqualTo("GET");
        Assertions.assertThat(httpRequest.getRequestPath().getTarget()).isEqualTo("/index.html");
        Assertions.assertThat(httpRequest.getVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    @DisplayName("POST로 요청 후 body에 값을 담아도, HttpRequest 객체가 올바르게 생성된다")
    void createHttpRequestWithBodyTest() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("POST /user/create?id=1 HTTP/1.1\n");
        stringBuilder.append("Host: localhost:8080\n");
        stringBuilder.append("Connection: keep-alive\n");
        stringBuilder.append("Content-Length: 46\n");
        stringBuilder.append("Content-Type: application/x-www-form-urlencoded\n");
        stringBuilder.append("Accept: */*\n\n");
        stringBuilder.append("userId=javajigi&password=password&name=JaeSung\n");
        inputStream = new ByteArrayInputStream(stringBuilder.toString().getBytes());

        HttpRequest request = new HttpRequest(inputStream);

        assertEquals("POST", request.getMethod().getName());
        assertEquals("/user/create", request.getRequestPath().getTarget());
        assertEquals("keep-alive", request.getRequestHeaderByKey("Connection"));
        assertEquals("1", request.getRequestPath().getParameterByKey("id"));
        assertEquals("javajigi", request.getRequestBody().getParameterByKey("userId"));
    }

    @Test
    @DisplayName("예외 테스트: HttpRequest 생성 중 잘못된 값이 전달되면, 예외가 발생한다")
    void createHttpRequestExceptionTest() {
        String invalidRequest = "Es-Muss-Sein!";
        inputStream = new ByteArrayInputStream(invalidRequest.getBytes());

        Assertions.assertThatThrownBy(() -> new HttpRequest(inputStream))
                .isInstanceOf(InvalidHttpRequestException.class)
                .hasMessage("Http Request의 값이 올바르지 않습니다");
    }

    @Test
    @DisplayName("HttpRequest에 contentType 정보를 요청하면, 올바른 값을 반환한다")
    void getContentTypeTest() {
        HttpRequest httpRequest = new HttpRequest(inputStream);

        Assertions.assertThat(httpRequest.getAcceptType()).isEqualTo("*/*");
    }

    @Test
    @DisplayName("HttpRequest에 Version 정보를 요청하면, 올바른 값을 반환한다")
    void getVersionTest() {
        HttpRequest httpRequest = new HttpRequest(inputStream);

        Assertions.assertThat(httpRequest.getVersion()).isEqualTo("HTTP/1.1");
    }
}
