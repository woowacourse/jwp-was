package http.request;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {

    @Test
    void getPath() throws IOException {
        String request = "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "";
        InputStream in = IOUtils.toInputStream(request, "UTF-8");

        HttpRequest httpRequest = RequestFactory.createHttpRequest(in);

        assertThat(httpRequest.getPath()).isEqualTo("/index.html");
    }

    @Test
    void getPath_파라미터_있는경우() throws IOException {
        String requestWithParameters = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "";
        InputStream in = IOUtils.toInputStream(requestWithParameters, "UTF-8");

        HttpRequest httpRequest = RequestFactory.createHttpRequest(in);

        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
    }



    @Test
    void getHeader() throws IOException {
        String request = "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "";
        InputStream in = IOUtils.toInputStream(request, "UTF-8");

        HttpRequest httpRequest = RequestFactory.createHttpRequest(in);

        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getHeader("Accept")).isEqualTo("*/*");
    }

    @Test
    void getBody() throws IOException {
        String requestWithBody = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 93\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        InputStream in = IOUtils.toInputStream(requestWithBody, "UTF-8");

        HttpRequest httpRequest = RequestFactory.createHttpRequest(in);

        HttpBody expectedBody = HttpBody.of("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        assertThat(httpRequest.getBody()).isEqualTo(expectedBody);
    }
}