package webserver.http.response;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import utils.FileIoUtils;

class HttpResponseTest {
    @DisplayName("200상태의 HttpResponse를 생성한다.")
    @Test
    void of() throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        HttpResponse httpResponse = HttpResponse.ok()
            .contentType("text/html;charset=utf-8")
            .contentLength(body.length)
            .body(body)
            .build();

        assertThat(httpResponse).isNotNull();
    }
}