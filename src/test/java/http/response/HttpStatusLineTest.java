package http.response;

import http.HttpVersion;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpStatusLineTest {
    @Test
    void StatusLine_toString() {
        HttpStatusLine statusLine = new HttpStatusLine(HttpVersion.HTTP_1_1, HttpStatus.OK);

        assertThat(statusLine.toString()).isEqualTo("HTTP/1.1 200 OK");
    }
}