package webserver.http;

import org.junit.jupiter.api.Test;
import webserver.WebTestForm;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest extends WebTestForm {

    @Test
    void HttpRequest_생성후_path_확인() throws IOException {
        HttpRequest httpRequest = getHttpGetRequest("/");
        String path = httpRequest.getPath();
        assertThat(path).isEqualTo("/");
    }
}