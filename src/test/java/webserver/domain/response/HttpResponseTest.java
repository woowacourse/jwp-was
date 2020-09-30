package webserver.domain.response;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import utils.FileIoUtils;

class HttpResponseTest {
    @DisplayName("200상태의 HttpResponse를 생성한다.")
    @Test
    void of() throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        Map<String, String > headerFields = new HashMap<>();
        headerFields.put("Content-Type", "text/html;charset=utf-8");
        headerFields.put("Content-Length", String.valueOf(body.length));
        ResponseHeader responseHeader = new ResponseHeader(headerFields);
        HttpResponse httpResponse = HttpResponse.of("200", responseHeader, body);

        assertThat(httpResponse.getStatusLine().getValue()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(httpResponse.getHeader()).contains("Content-Type: text/html;charset=utf-8",
            "Content-Length: 6902");
        assertThat(httpResponse.getBody()).isEqualTo(body);
    }
}