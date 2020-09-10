package web;

import exception.InvalidHttpRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtilsTest;
import web.request.HttpRequest;

import java.io.*;

public class HttpRequestTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @Test
    @DisplayName("HttpRequest 객체가 올바르게 생성된다")
    void createHttpRequest() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GET /index.html HTTP/1.1\n");
        stringBuilder.append("Host: localhost:8080\n");
        stringBuilder.append("Connection: keep-alive\n");
        stringBuilder.append("Accept: */*\n\n");
        InputStream inputStream = new ByteArrayInputStream(stringBuilder.toString().getBytes());
        try {
            BufferedReader request = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            HttpRequest httpRequest = new HttpRequest(request);
            Assertions.assertThat(httpRequest.getMethod()).isEqualTo("GET");
            Assertions.assertThat(httpRequest.getTarget()).isEqualTo("/index.html");
            Assertions.assertThat(httpRequest.getVersion()).isEqualTo("HTTP/1.1");
        } catch(IOException e) {
            logger.error(e.getMessage());
            throw new AssertionError();
        }
    }

    @Test
    @DisplayName("HttpRequest 생성 중 잘못된 값이 전달되면, 예외가 발생한다")
    void createHttpRequestException() {
        String invalidRequest = "Es Muss Sein!";
        InputStream inputStream = new ByteArrayInputStream(invalidRequest.getBytes());
        try {
            BufferedReader request = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            Assertions.assertThatThrownBy(() -> new HttpRequest(request))
                    .isInstanceOf(InvalidHttpRequestException.class)
                    .hasMessage("Http Request의 값이 올바르지 않습니다");
        } catch(IOException e) {
            logger.error(e.getMessage());
            throw new AssertionError();
        }
    }
}
