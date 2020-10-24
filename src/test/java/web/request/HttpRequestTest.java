package web.request;

import exception.InvalidHttpRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);
    private static final String TEST_DIRECTORY = "./src/test/resources/request/";
    private InputStream inputStream;

    @Test
    @DisplayName("GET 요청을 보낼 경우, HttpRequest 객체가 올바르게 생성된다")
    void createHttpRequestTest() {
        try {
            inputStream = new FileInputStream(new File(TEST_DIRECTORY + "HTTP_GET_1.txt"));
            HttpRequest httpRequest = new HttpRequest(inputStream);
            Assertions.assertThat(httpRequest.getMethod().getName()).isEqualTo("GET");
            Assertions.assertThat(httpRequest.getTarget()).isEqualTo("/index.html");
            Assertions.assertThat(httpRequest.getVersion()).isEqualTo("HTTP/1.1");
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            throw new AssertionError();
        }
    }

    @Test
    @DisplayName("POST로 요청을 보낼 경우, HttpRequest 객체가 올바르게 생성된다")
    void createHttpRequestWithBodyTest() {
        try {
            inputStream = new FileInputStream(new File(TEST_DIRECTORY + "HTTP_POST_1.txt"));

            HttpRequest request = new HttpRequest(inputStream);

            assertEquals("POST", request.getMethod().getName());
            assertEquals("/user/create", request.getTarget());
            assertEquals("keep-alive", request.getRequestHeaderByKey("Connection"));
            assertEquals("javajigi", request.getRequestBodyByKey("userId"));
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            throw new AssertionError();
        }
    }

    @Test
    @DisplayName("POST로 요청 후 경로에 값을 담을 경우, HttpRequest 객체가 올바르게 생성된다")
    void createHttpRequestWithBodyAndParamTest() {
        try {
            inputStream = new FileInputStream(new File(TEST_DIRECTORY + "HTTP_POST_2.txt"));

            HttpRequest request = new HttpRequest(inputStream);

            assertEquals("POST", request.getMethod().getName());
            assertEquals("/user/create", request.getTarget());
            assertEquals("keep-alive", request.getRequestHeaderByKey("Connection"));
            assertEquals("1", request.getRequestParamsByKey("id"));
            assertEquals("javajigi", request.getRequestBodyByKey("userId"));
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            throw new AssertionError();
        }
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
        try {
            inputStream = new FileInputStream(new File(TEST_DIRECTORY + "HTTP_GET_1.txt"));
            HttpRequest httpRequest = new HttpRequest(inputStream);

            Assertions.assertThat(httpRequest.getAcceptType()).isEqualTo("*/*");
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            throw new AssertionError();
        }
    }
}
