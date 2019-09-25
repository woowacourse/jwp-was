package webserver.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {
    private static final String GET_REQUEST_MESSAGE =
            "GET /index.html HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: */*";

    private static final String POST_REQUEST_MESSAGE =
            "POST /user/create HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Content-Length: 59\n" +
                    "Content-Type: application/x-www-form-urlencoded\n" +
                    "Accept: */*\n" +
                    "\n" +
                    "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

    private static final String GET_REQUEST_MESSAGE_WITH_QUERY_STRING =
            "GET /user/create?userId=done&password=12345678&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=done%40slipp.net HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: */*";

    private RequestLine requestLineOfGetMessage;
    private RequestLine requestLineOfPostMessage;
    private RequestLine requestLineWithQueryString;

    @BeforeEach
    void setUp() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(GET_REQUEST_MESSAGE.getBytes())));
        requestLineOfGetMessage = RequestLine.of(br);
        br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(POST_REQUEST_MESSAGE.getBytes())));
        requestLineOfPostMessage = RequestLine.of(br);
        br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(GET_REQUEST_MESSAGE_WITH_QUERY_STRING.getBytes())));
        requestLineWithQueryString = RequestLine.of(br);
    }

    @DisplayName("HttpRequestLine 생성")
    @Test
    void of() {
        assertThat(requestLineOfGetMessage.getMethod()).isEqualTo("GET");
        assertThat(requestLineOfGetMessage.getTarget()).isEqualTo("/index.html");
        assertThat(requestLineOfGetMessage.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @DisplayName("GET 메서드인지 확인")
    @Test
    void isGet() {
        assertThat(requestLineOfGetMessage.isGet()).isTrue();
        assertThat(requestLineOfPostMessage.isGet()).isFalse();
        assertThat(requestLineWithQueryString.isGet()).isTrue();
    }

    @DisplayName("POST 메서드인지 확인")
    @Test
    void isPost() {
        assertThat(requestLineOfGetMessage.isPost()).isFalse();
        assertThat(requestLineOfPostMessage.isPost()).isTrue();
        assertThat(requestLineWithQueryString.isPost()).isFalse();
    }

    @DisplayName("query string이 있는지 확인")
    @Test
    void hasParameters() {
        assertThat(requestLineWithQueryString.hasParameters()).isTrue();
        assertThat(requestLineOfGetMessage.hasParameters()).isFalse();
    }

    @DisplayName("요청자원 추출하는지 확인")
    @Test
    void getPath() {
        assertThat(requestLineOfGetMessage.getPath()).isEqualTo("/index.html");
        assertThat(requestLineOfPostMessage.getPath()).isEqualTo("/user/create");
        assertThat(requestLineWithQueryString.getPath()).isEqualTo("/user/create");
    }

    @DisplayName("query string으로 전달된 데이터 조회")
    @Test
    void get() {
        assertThat(requestLineWithQueryString.get("userId")).isEqualTo("done");
        assertThat(requestLineWithQueryString.get("password")).isEqualTo("12345678");
        assertThat(requestLineWithQueryString.get("name")).isEqualTo("박재성");
        assertThat(requestLineWithQueryString.get("email")).isEqualTo("done@slipp.net");
    }
}
