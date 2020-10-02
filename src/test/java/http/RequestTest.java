package http;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import http.request.Request;
import http.request.RequestMethod;

class RequestTest {

    @DisplayName("post Request 생성 테스트")
    @Test
    void postRequestTest() throws Exception {
        String request = "POST /user/create HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Content-Length: 93\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "Accept: */*\n"
                + "\n"
                + "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        Request expected = new Request(br);

        assertThat(expected.getRequestLine().getUrl()).isEqualTo("/user/create");
        assertThat(expected.getRequestHeader().getRequestHeaders()).hasSize(5);
        assertThat(expected.getRequestBody().getBody()).isEqualTo(
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }

    @DisplayName("get Request 생성 테스트")
    @Test
    void getRequestTest() throws Exception {
        String request =
                "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n"
                        + "Host: localhost:8080\n"
                        + "Connection: keep-alive\n"
                        + "Accept: */*";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        Request expected = new Request(br);

        assertThat(expected.getRequestLine().getUrl()).isEqualTo(
                "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        assertThat(expected.getRequestLine().getMethod()).isEqualTo(RequestMethod.GET);
        assertThat(expected.getRequestHeader().getRequestHeaders()).hasSize(3);
        assertThat(expected.getRequestBody().getBody()).hasSize(0);
    }
}
