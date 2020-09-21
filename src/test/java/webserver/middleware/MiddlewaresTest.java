package webserver.middleware;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class MiddlewaresTest {
    @Test
    void run() throws IOException {
        String message = "GET /index.html HTTP/1.1";
        ByteArrayInputStream in = new ByteArrayInputStream(message.getBytes());
        HttpRequest httpRequest = HttpRequest.from(in);
        ByteArrayOutputStream out  = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.from(out, httpRequest.version());
        new Middlewares().run(httpRequest, httpResponse);
    }

}
