package webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.emptyList;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import webserver.filter.ServeStatic;

public class ApplicationFilterChainTest {
    @Test
    void run() throws IOException {
        String message = "GET /page HTTP/1.1";
        ByteArrayInputStream in = new ByteArrayInputStream(message.getBytes());
        HttpRequest httpRequest = HttpRequest.from(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.from(out, httpRequest.version());
        new ApplicationFilterChain(Arrays.asList(new ServeStatic("templates")), emptyList())
            .run(httpRequest, httpResponse);
        assertThat(out.toString()).isEqualTo("HTTP/1.1 404 Not Found\n");
    }
}
