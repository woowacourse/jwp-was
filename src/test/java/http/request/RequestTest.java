package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestTest {
    @Test
    @DisplayName("Post 요청을 보냈을 때")
    void request_POST() throws IOException {
        String header = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 46\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=JaeSung";

        InputStream in = new ByteArrayInputStream(header.getBytes());
        RequestParser requestParser = new RequestParser(in);
        Request request = new Request(requestParser.getHeaderInfo(), requestParser.getParameter());

        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(requestParser.getParameter().get("userId")).isEqualTo("javajigi");
    }

    @Test
    @DisplayName("Get 요청을 보냈을 때")
    void request_GET() throws Exception {
        String header = "GET /user/create?userId=javajigi&password=password&name=JaeSung HTTP/1.1 \n" +
                "Host: localhost:8080 \n" +
                "Connection: keep-alive \n" +
                "Accept: */*";
        InputStream in = new ByteArrayInputStream(header.getBytes());
        RequestParser requestParser = new RequestParser(in);
        Request request = new Request(requestParser.getHeaderInfo(), requestParser.getParameter());

        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
    }

    @Test
    @DisplayName("다른 Post 요청을 보냈을 때")
    void request_POST2() throws Exception {
        String header = "POST /user/create?id=1 HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 46\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=JaeSung";

        InputStream in = new ByteArrayInputStream(header.getBytes());
        RequestParser requestParser = new RequestParser(in);
        Request request = new Request(requestParser.getHeaderInfo(), requestParser.getParameter());

        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getParameter("id")).isEqualTo("1");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }
}