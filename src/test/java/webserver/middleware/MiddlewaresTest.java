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

public class MiddlewaresTest {
    @Test
    void run() throws IOException, URISyntaxException {
        String message = "GET /page HTTP/1.1";
        ByteArrayInputStream in = new ByteArrayInputStream(message.getBytes());
        HttpRequest httpRequest = HttpRequest.from(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.from(out, httpRequest.version());
        new Middlewares()
            .chain(new ServeStatic("templates"))
            .chain(new NotFound())
            .run(httpRequest, httpResponse);
        byte[] file = FileIoUtils.loadFileFromClasspath("./templates/error/not_found.html");
        assertThat(out.toString()).isEqualTo("HTTP/1.1 404 Not Found\r\n"
            + "Content-Length: " + file.length + "\r\n"
            + "Content-Type: text/html;charset=utf-8\r\n\r\n" + new String(file));
    }
}
