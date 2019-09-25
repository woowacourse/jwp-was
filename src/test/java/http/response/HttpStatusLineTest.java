package http.response;

import http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpStatusLineTest {

    @Test
    void testToString() {
        HttpStatusLine httpStatusLine = new HttpStatusLine(HttpStatus.OK);
        assertThat(httpStatusLine.toString()).isEqualTo("HTTP/1.1 200 OK\r\n");
    }
}