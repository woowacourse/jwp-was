package http.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpStatusLineTest {

    @Test
    void testToString() {
        String line = "HTTP/1.1 200 OK";
        HttpStatusLine httpStatusLine = new HttpStatusLine(line);
        assertThat(httpStatusLine.toString()).isEqualTo("HTTP/1.1 200 Ok\n");
    }
}