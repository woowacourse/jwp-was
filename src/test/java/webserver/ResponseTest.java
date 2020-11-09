package webserver;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ResponseTest {

    @ParameterizedTest
    @CsvSource(value = {
        "GET /index.html HTTP/1.1:./templates/index.html",
        "GET / HTTP/1.1:./templates/index.html",
        "GET /favicon.ico HTTP/1.1:./templates/favicon.ico",
        "GET /main.css HTTP/1.1:./static/main.css",
        "GET /main.js HTTP/1.1:./static/main.js",
    }, delimiter = ':')
    void getPath(String input, String expected) throws IOException {
        Request request = new Request(new ByteArrayInputStream(input.getBytes()));
        Response response = Response.of(request);
        assertThat(response.getPath()).isEqualTo(expected);
    }
}
