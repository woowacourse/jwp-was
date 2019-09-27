package webserver.http;

import org.junit.jupiter.api.Test;
import webserver.WebTestForm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest extends WebTestForm {

    @Test
    void HttpRequest_생성후_path_확인() throws IOException {
        InputStream in = new ByteArrayInputStream(getHttpGetRequest("/").getBytes());
        HttpRequest httpRequest = HttpRequest.create(in);
        String path = httpRequest.getPath();
        assertThat(path).isEqualTo("/");
    }

    @Test
    void HttpRequest_생성후_sessionId_확인() throws IOException {
        InputStream in = new ByteArrayInputStream(getHttpGetRequest("/").getBytes());
        HttpRequest httpRequest = HttpRequest.create(in);
        String sessionId = httpRequest.getSessionId();
        assertThat(sessionId).isEqualTo("123");
    }
}