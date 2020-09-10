package web;

import exception.InvalidHttpRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtilsTest;
import web.request.HttpRequest;

import java.io.*;

public class HttpRequestTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);
    private InputStream inputStream;

    @BeforeEach
    void setup(){
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
    @DisplayName("예외 테스트: HttpRequest 생성 중 잘못된 값이 전달되면, 예외가 발생한다")
    void createHttpRequestExceptionTest() {
        String invalidRequest = "Es Muss Sein!";
        inputStream = new ByteArrayInputStream(invalidRequest.getBytes());
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

    @Test
    @DisplayName("HttpRequest에 contentType 정보를 요청하면, 올바른 값을 반환함")
    void getContentTypeTest() {
        try {
            BufferedReader request = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            HttpRequest httpRequest = new HttpRequest(request);

            Assertions.assertThat(httpRequest.getContentType()).isEqualTo("*/*");
        } catch(IOException e) {
            logger.error(e.getMessage());
            throw new AssertionError();
        }
    }
}
