package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

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

        assertThat(expected.isMethod(RequestMethod.POST)).isTrue();
        assertThat(expected.getRequestLine().getPath()).isEqualTo("/user/create");
        assertThat(expected.getRequestHeaders()).hasSize(5);
        assertThat(expected.getRequestBody().getBody()).isEqualTo(
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }

    @DisplayName("쿼리스트링과 바디에 데이터가 전해질 때 post Request 생성 테스트")
    @Test
    void postRequestWithBodyTest() throws Exception {
        String request = "POST /user/create?id=1 HTTP/1.1\n"
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

        assertThat(expected.isMethod(RequestMethod.POST)).isTrue();
        assertThat(expected.getRequestLine().getPath()).isEqualTo("/user/create");
        assertThat(expected.getQueryParams().isEmpty()).isFalse();
        assertThat(expected.getRequestHeaders()).hasSize(5);
        assertThat(expected.getRequestBody().getBody()).isEqualTo(
                "userId=javajigi&password=password&name=JaeSung");
    }

    @DisplayName("쿼리스트링 없는 get Request 생성 테스트")
    @Test
    void getRequestTest() throws Exception {
        String request = "GET /index.html HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        Request expected = new Request(br);

        assertThat(expected.isMethod(RequestMethod.GET)).isTrue();
        assertThat(expected.getPath()).isEqualTo("/index.html");
        assertThat(expected.getQueryParams().isEmpty()).isTrue();
        assertThat(expected.getRequestLine().getMethod()).isEqualTo(RequestMethod.GET);
        assertThat(expected.getRequestHeaders()).hasSize(3);
        assertThat(expected.getRequestBody().getBody()).hasSize(0);
    }

    @DisplayName("쿼리스트링 있는 get Request 생성 테스트")
    @Test
    void getRequestWithQueryStringTest() throws Exception {
        String request =
                "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n"
                        + "Host: localhost:8080\n"
                        + "Connection: keep-alive\n"
                        + "Accept: */*";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        Request expected = new Request(br);

        assertThat(expected.isMethod(RequestMethod.GET)).isTrue();
        assertThat(expected.getPath()).isEqualTo("/user/create");
        assertThat(expected.getQueryParams().isEmpty()).isFalse();
        assertThat(expected.getRequestLine().getMethod()).isEqualTo(RequestMethod.GET);
        assertThat(expected.getRequestHeaders()).hasSize(3);
        assertThat(expected.getRequestBody().getBody()).hasSize(0);
    }
}
