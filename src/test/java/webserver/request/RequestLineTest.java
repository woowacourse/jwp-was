package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static controller.support.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static support.Constants.*;
import static webserver.support.ConStants.METHOD_GET;

public class RequestLineTest {
    private static final String GET_REQUEST_STATUS_LINE = "GET /index.html HTTP/1.1\n";
    private static final String POST_REQUEST_STATUS_LINE = "POST /user/create HTTP/1.1\n";
    private static final String GET_REQUEST_STATUS_LINE_WITH_QUERY_STRING =
            "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n";


    @DisplayName("HttpRequestLine 생성")
    @Test
    void of() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(GET_REQUEST_STATUS_LINE.getBytes())));
        RequestLine requestLineOfGetMessage = RequestLine.of(br);

        assertThat(requestLineOfGetMessage.getMethod()).isEqualTo(METHOD_GET);
        assertThat(requestLineOfGetMessage.getTarget()).isEqualTo("/index.html");
        assertThat(requestLineOfGetMessage.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @DisplayName("GET 메서드인지 확인")
    @Test
    void isGet() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(GET_REQUEST_STATUS_LINE.getBytes())));
        RequestLine requestLineOfGetMessage = RequestLine.of(br);
        br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(POST_REQUEST_STATUS_LINE.getBytes())));
        RequestLine requestLineOfPostMessage = RequestLine.of(br);

        assertThat(requestLineOfGetMessage.isGet()).isTrue();
        assertThat(requestLineOfPostMessage.isGet()).isFalse();
    }

    @DisplayName("POST 메서드인지 확인")
    @Test
    void isPost() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(GET_REQUEST_STATUS_LINE.getBytes())));
        RequestLine requestLineOfGetMessage = RequestLine.of(br);
        br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(POST_REQUEST_STATUS_LINE.getBytes())));
        RequestLine requestLineOfPostMessage = RequestLine.of(br);

        assertThat(requestLineOfGetMessage.isPost()).isFalse();
        assertThat(requestLineOfPostMessage.isPost()).isTrue();
    }

    @DisplayName("query string이 있는지 확인")
    @Test
    void hasParameters() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(GET_REQUEST_STATUS_LINE.getBytes())));
        RequestLine requestLineOfGetMessage = RequestLine.of(br);
        br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(GET_REQUEST_STATUS_LINE_WITH_QUERY_STRING.getBytes())));
        RequestLine requestLineWithQueryString = RequestLine.of(br);

        assertThat(requestLineWithQueryString.hasParameters()).isTrue();
        assertThat(requestLineOfGetMessage.hasParameters()).isFalse();
    }

    @DisplayName("요청자원 추출하는지 확인")
    @Test
    void getPath() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(GET_REQUEST_STATUS_LINE.getBytes())));
        RequestLine requestLineOfGetMessage = RequestLine.of(br);
        br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(GET_REQUEST_STATUS_LINE_WITH_QUERY_STRING.getBytes())));
        RequestLine requestLineWithQueryString = RequestLine.of(br);

        assertThat(requestLineOfGetMessage.getPath()).isEqualTo("/index.html");
        assertThat(requestLineWithQueryString.getPath()).isEqualTo("/user/create");
    }

    @DisplayName("query string으로 전달된 데이터 조회")
    @Test
    void get() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(GET_REQUEST_STATUS_LINE_WITH_QUERY_STRING.getBytes())));
        RequestLine requestLineWithQueryString = RequestLine.of(br);

        assertThat(requestLineWithQueryString.getParameterValue(USER_ID)).isEqualTo(TEST_USER_ID);
        assertThat(requestLineWithQueryString.getParameterValue(USER_PASSWORD)).isEqualTo(TEST_USER_PASSWORD);
        assertThat(requestLineWithQueryString.getParameterValue(USER_NAME)).isEqualTo(TEST_USER_NAME);
        assertThat(requestLineWithQueryString.getParameterValue(USER_EMAIL)).isEqualTo(TEST_USER_EMAIL);
    }
}
