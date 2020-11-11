package http;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpResponseTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    @DisplayName("200 Ok 반환")
    void response200Test() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        HttpResponse httpResponse = new HttpResponse(out, HttpStatus.OK, "HTTP/1.1");
        httpResponse.response200Header(10, MimeType.HTML.getContentType());

        String result = out.toString();
        String expected = "HTTP/1.1 200 OK\r\n"
            + "Content-Length: 10\r\n"
            + "Content-Type: text/html\r\n"
            + "\r\n";

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("302 Found 반환")
    void response302Test() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        HttpResponse httpResponse = new HttpResponse(out, HttpStatus.FOUND, "HTTP/1.1");
        httpResponse.response302Header(10, MimeType.HTML.getContentType(), "/index.html");

        String result = out.toString();
        String expected = "HTTP/1.1 302 FOUND\r\n"
            + "Content-Length: 10\r\n"
            + "Content-Type: text/html\r\n"
            + "Location: /index.html\r\n"
            + "\r\n";
        assertEquals(expected, result);
    }

}
