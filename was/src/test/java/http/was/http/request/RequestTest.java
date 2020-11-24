package http.was.http.request;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import http.was.common.TestFileIo;
import http.was.http.HttpMethod;

class RequestTest {

    @DisplayName("post Request 생성 테스트")
    @Test
    void postRequestTest() throws Exception {
        BufferedReader br = TestFileIo.readBufferedReader("http_user_post_request.txt");
        Request expected = new Request(br);

        assertThat(expected.isMethod(HttpMethod.POST)).isTrue();
        assertThat(expected.getRequestLine().getPath()).isEqualTo("/users");
        assertThat(expected.getRequestHeaders()).hasSize(5);
        assertThat(expected.getRequestBody().getBody()).isEqualTo(
                "userId=javajigi&password=password&name=JaeSung&email=javajigi%40slipp.net");
    }

    @DisplayName("쿼리스트링과 바디에 데이터가 전해질 때 post Request 생성 테스트")
    @Test
    void postRequestWithBodyTest() throws Exception {
        String request = "POST /users?id=1 HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Content-Length: 46\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "Accept: */*\n"
                + "\n"
                + "userId=javajigi&password=password&name=JaeSung";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        Request expected = new Request(br);

        assertThat(expected.isMethod(HttpMethod.POST)).isTrue();
        assertThat(expected.getRequestLine().getPath()).isEqualTo("/users");
        assertThat(expected.getQueryParams().isEmpty()).isFalse();
        assertThat(expected.getRequestHeaders()).hasSize(5);
        assertThat(expected.getRequestBody().getBody()).isEqualTo(
                "userId=javajigi&password=password&name=JaeSung");
    }

    @DisplayName("쿼리스트링 없는 get Request 생성 테스트")
    @Test
    void getRequestTest() throws Exception {
        BufferedReader br = TestFileIo.readBufferedReader("http_resource_request.txt");

        Request expected = new Request(br);

        assertThat(expected.isMethod(HttpMethod.GET)).isTrue();
        assertThat(expected.getPath()).isEqualTo("/index.html");
        assertThat(expected.getQueryParams().isEmpty()).isTrue();
        assertThat(expected.getRequestLine().getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(expected.getRequestHeaders()).hasSize(3);
        assertThat(expected.getRequestBody().getBody()).hasSize(0);
    }

    @DisplayName("쿼리스트링 있는 get Request 생성 테스트")
    @Test
    void getRequestWithQueryStringTest() throws Exception {
        BufferedReader br = TestFileIo.readBufferedReader("http_user_get_request.txt");
        Request expected = new Request(br);

        assertThat(expected.isMethod(HttpMethod.GET)).isTrue();
        assertThat(expected.getPath()).isEqualTo("/users");
        assertThat(expected.getQueryParams().isEmpty()).isFalse();
        assertThat(expected.getRequestLine().getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(expected.getRequestHeaders()).hasSize(3);
        assertThat(expected.getRequestBody().getBody()).hasSize(0);
    }
}
