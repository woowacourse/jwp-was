package webserver.domain.response;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import utils.FileIoUtils;

class HttpResponseTest {
    private static final String lineSeparator = System.getProperty("line.separator");

    @DisplayName("200상태의 HttpResponse를 생성한다.")
    @Test
    void of() throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        HttpResponse httpResponse = HttpResponse.of("200", body);

        assertThat(httpResponse.getStatusLine().getValue()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(httpResponse.getHeader()).isEqualTo("Content-Type: text/html;charset=utf-8" + lineSeparator +
            "Content-Length: 6902" + lineSeparator);
        assertThat(httpResponse.getBody()).isEqualTo(body);
    }
}