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

public class HttpRequestTest {
    private static final String GET_REQUEST_MESSAGE =
            "GET /index.html HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: */*";

    private static final String POST_REQUEST_MESSAGE =
            "POST /user/create HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Content-Length: 93\n" +
                    "Content-Type: application/x-www-form-urlencoded\n" +
                    "Accept: */*\n" +
                    "\n" +
                    "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

    private static final String GET_REQUEST_MESSAGE_WITH_QUERY_STRING =
            "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: */*";

    @DisplayName("HttpRequest 생성")
    @Test
    void of() throws IOException {
        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(GET_REQUEST_MESSAGE.getBytes()));
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(GET_REQUEST_MESSAGE.getBytes())));
        RequestLine requestLine = RequestLine.of(br);
        RequestHeader requestHeader = RequestHeader.of(br);
        RequestBody requestBody = RequestBody.of(br, requestHeader.getContentLength());

        assertThat(httpRequest.getRequestLine()).isEqualTo(requestLine);
        assertThat(httpRequest.getRequestHeader()).isEqualTo(requestHeader);
        assertThat(httpRequest.getRequestBody()).isEqualTo(requestBody);
    }

    @DisplayName("query string에 데이터가 있는 경우에 요청 메시지의 데이터를 조회하는지 확인")
    @Test
    void getParametersInQueryString() {
        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(GET_REQUEST_MESSAGE_WITH_QUERY_STRING.getBytes()));
        assertThat(httpRequest.getParameter(USER_ID)).isEqualTo(TEST_USER_ID);
        assertThat(httpRequest.getParameter(USER_PASSWORD)).isEqualTo(TEST_USER_PASSWORD);
        assertThat(httpRequest.getParameter(USER_NAME)).isEqualTo(TEST_USER_NAME);
        assertThat(httpRequest.getParameter(USER_EMAIL)).isEqualTo(TEST_USER_EMAIL);
    }

    @DisplayName("요청 메시지의 바디에 데이터가 있는 경우에 요청 메시지의 데이터를 조회하는지 확인")
    @Test
    void getParametersInRequestBody() {
        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(POST_REQUEST_MESSAGE.getBytes()));
        assertThat(httpRequest.getParameter(USER_ID)).isEqualTo(TEST_USER_ID);
        assertThat(httpRequest.getParameter(USER_PASSWORD)).isEqualTo(TEST_USER_PASSWORD);
        assertThat(httpRequest.getParameter(USER_NAME)).isEqualTo(TEST_USER_NAME);
        assertThat(httpRequest.getParameter(USER_EMAIL)).isEqualTo(TEST_USER_EMAIL);
    }
}
