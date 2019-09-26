package webserver.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestStatusLineTest {
    private static final String GET_REQUEST_STATUS_LINE = "GET /index.html HTTP/1.1\n";
    private static final String POST_REQUEST_STATUS_LINE = "POST /user/create HTTP/1.1\n";
    private static final String GET_REQUEST_STATUS_LINE_WITH_QUERY_STRING =
            "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n";

    private RequestStatusLine requestStatusLineOfGetMessage;
    private RequestStatusLine requestStatusLineOfPostMessage;
    private RequestStatusLine requestStatusLineWithQueryString;

    @BeforeEach
    void setUp() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(GET_REQUEST_STATUS_LINE.getBytes())));
        requestStatusLineOfGetMessage = RequestStatusLine.of(br);
        br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(POST_REQUEST_STATUS_LINE.getBytes())));
        requestStatusLineOfPostMessage = RequestStatusLine.of(br);
        br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(GET_REQUEST_STATUS_LINE_WITH_QUERY_STRING.getBytes())));
        requestStatusLineWithQueryString = RequestStatusLine.of(br);
    }

    @DisplayName("HttpRequestLine 생성")
    @Test
    void of() {
        assertThat(requestStatusLineOfGetMessage.getMethod()).isEqualTo("GET");
        assertThat(requestStatusLineOfGetMessage.getTarget()).isEqualTo("/index.html");
        assertThat(requestStatusLineOfGetMessage.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @DisplayName("GET 메서드인지 확인")
    @Test
    void isGet() {
        assertThat(requestStatusLineOfGetMessage.isGet()).isTrue();
        assertThat(requestStatusLineOfPostMessage.isGet()).isFalse();
        assertThat(requestStatusLineWithQueryString.isGet()).isTrue();
    }

    @DisplayName("POST 메서드인지 확인")
    @Test
    void isPost() {
        assertThat(requestStatusLineOfGetMessage.isPost()).isFalse();
        assertThat(requestStatusLineOfPostMessage.isPost()).isTrue();
        assertThat(requestStatusLineWithQueryString.isPost()).isFalse();
    }

    @DisplayName("query string이 있는지 확인")
    @Test
    void hasParameters() {
        assertThat(requestStatusLineWithQueryString.hasParameters()).isTrue();
        assertThat(requestStatusLineOfGetMessage.hasParameters()).isFalse();
    }

    @DisplayName("요청자원 추출하는지 확인")
    @Test
    void getPath() {
        assertThat(requestStatusLineOfGetMessage.getPath()).isEqualTo("/index.html");
        assertThat(requestStatusLineOfPostMessage.getPath()).isEqualTo("/user/create");
        assertThat(requestStatusLineWithQueryString.getPath()).isEqualTo("/user/create");
    }

    @DisplayName("query string으로 전달된 데이터 조회")
    @Test
    void get() {
        assertThat(requestStatusLineWithQueryString.getParameterValue("userId")).isEqualTo("javajigi");
        assertThat(requestStatusLineWithQueryString.getParameterValue("password")).isEqualTo("password");
        assertThat(requestStatusLineWithQueryString.getParameterValue("name")).isEqualTo("박재성");
        assertThat(requestStatusLineWithQueryString.getParameterValue("email")).isEqualTo("javajigi@slipp.net");
    }
}
