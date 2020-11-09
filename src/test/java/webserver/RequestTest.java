package webserver;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class RequestTest {

    @Test
    void parse() throws IOException {
        String input = "GET /index.html HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Request request = new Request(inputStream);
        assertThat(request).hasToString(input);
    }

    @Test
    public void parseNull() throws IOException {
        Request request = new Request(null);
        assertThat(request).hasToString("");
    }

    @Test
    void getQueryParameters() throws IOException {
        String input = "GET /user/create?userId=javajigi&password=password&name=jaesung&email=javajigi@slipp.net HTTP/1.1";
        Request request = new Request(new ByteArrayInputStream(input.getBytes()));
        Map<String, String> queryParameters = request.getQueryParameters();

        assertThat(queryParameters.get("userId")).isEqualTo("javajigi");
        assertThat(queryParameters.get("password")).isEqualTo("password");
        assertThat(queryParameters.get("name")).isEqualTo("jaesung");
        assertThat(queryParameters.get("email")).isEqualTo("javajigi@slipp.net");
    }

    @Test
    void getEmptyQueryParameters() throws IOException {
        String input = "GET /user/create";
        Request request = new Request(new ByteArrayInputStream(input.getBytes()));
        Map<String, String> queryParameters = request.getQueryParameters();
        assertThat(queryParameters.keySet()).hasSize(0);
    }

    @Test
    void getMethod() throws IOException {
        String input = "GET /user/create?userId=javajigi&password=password&name=jaesung&email=javajigi@slipp.net HTTP/1.1";
        Request request = new Request(new ByteArrayInputStream(input.getBytes()));
        assertThat(request.getMethod()).isEqualTo(Method.GET);
    }
}
