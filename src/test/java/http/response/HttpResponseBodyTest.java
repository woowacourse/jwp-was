package http.response;

import http.MediaType;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseBodyTest {

    @Test
    void getBodyLength() throws IOException, URISyntaxException {
        HttpResponseBody httpResponseBody = new HttpResponseBody(FileIoUtils.loadFileFromClasspath(MediaType.getFullPath("/index.html")));
        assertThat(httpResponseBody.getBodyLength()).isEqualTo(6902);
    }

    @Test
    void getBody() {
        HttpResponseBody httpResponseBody = new HttpResponseBody("test".getBytes());
        assertThat(httpResponseBody.getBody()).isEqualTo("test".getBytes());
    }
}