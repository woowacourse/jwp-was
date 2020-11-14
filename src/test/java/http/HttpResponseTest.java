package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpResponseTest {
    @Test
    @DisplayName("200 Ok 반환")
    void response200Test() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        HttpResponse httpResponse = new HttpResponse(out, HttpStatus.OK, "HTTP/1.1");
        httpResponse.response200Header(10, MimeType.HTML.getContentType());

        String result = out.toString();

        assertThat(result).contains("HTTP/1.1 200 OK");
        assertThat(result).contains("Content-Length: 10");
        assertThat(result).contains("Content-Type: text/html;charset=utf-8");
    }

    @Test
    @DisplayName("302 Found 반환")
    void response302Test() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        HttpResponse httpResponse = new HttpResponse(out, HttpStatus.FOUND, "HTTP/1.1");
        httpResponse.response302Header(10, MimeType.HTML.getContentType(), "/index.html");

        String result = out.toString();

        assertThat(result).contains("HTTP/1.1 302 FOUND");
        assertThat(result).contains("Content-Length: 10");
        assertThat(result).contains("Content-Type: text/html;charset=utf-8");
        assertThat(result).contains("Location: /index.html");
    }

    @Test
    @DisplayName("405 Method Not Allowed 반환")
    void response405Test() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(out, HttpStatus.METHOD_NOT_ALLOWED, "HTTP/1.1");
        httpResponse.response405Header();

        String result = out.toString();
        String expected = "HTTP/1.1 405 METHOD NOT ALLOWED" + System.lineSeparator();
        assertEquals(expected, result);
    }
}
