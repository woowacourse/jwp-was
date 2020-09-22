package webserver.middleware;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

public class ServeStaticTest {
    @Test
    void service() throws IOException, URISyntaxException {
        String message = "GET /index.html HTTP/1.1";
        ByteArrayInputStream in = new ByteArrayInputStream(message.getBytes());
        HttpRequest httpRequest = HttpRequest.from(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.from(out, httpRequest.version());
        new ServeStatic("templates").service(httpRequest, httpResponse);
        byte[] file = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        assertThat(out.toString()).isEqualTo("HTTP/1.1 200 OK\r\n"
            + "Content-Length: " + file.length + "\r\n"
            + "Content-Type: text/html;charset=utf-8\r\n\r\n" + new String(file));
    }
}
