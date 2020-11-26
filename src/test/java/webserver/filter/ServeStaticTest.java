package webserver.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.emptyList;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.ApplicationFilterChain;

public class ServeStaticTest {
    @Test
    void service() throws IOException, URISyntaxException {
        String message = "GET /index.html HTTP/1.1";
        ByteArrayInputStream in = new ByteArrayInputStream(message.getBytes());
        HttpRequest httpRequest = HttpRequest.from(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.from(out, httpRequest.version());
        new ServeStatic("templates")
            .doFilter(httpRequest, httpResponse,
                new ApplicationFilterChain(emptyList(), emptyList()));
        byte[] file = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        assertThat(out.toString()).isEqualTo("HTTP/1.1 200 OK\n"
            + "Content-Length: " + file.length + "\n"
            + "Content-Type: text/html;charset=utf-8\n\n" + new String(file));
    }
}
