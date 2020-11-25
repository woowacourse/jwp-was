package http.response;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import utils.FileIoUtils;

public class HttpResponseBodyTest {

    @Test
    void init() {
        final HttpResponseBody httpResponseBody = new HttpResponseBody();

        assertThat(httpResponseBody.getBody().length).isEqualTo(0);
    }

    @Test
    void setBody() throws IOException, URISyntaxException {
        final HttpResponseBody httpResponseBody = new HttpResponseBody();
        final byte[] body = FileIoUtils.loadFileFromClasspath("templates/index.html");
        httpResponseBody.setBody(body);

        assertThat(httpResponseBody.getBody()).isEqualTo(body);
    }
}