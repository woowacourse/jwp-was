package http.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseStatusLineTest {

    @Test
    void testToString() {
        String line = "HTTP/1.1 200 OK";
        HttpResponseStatusLine httpResponseStatusLine = new HttpResponseStatusLine(line);
        assertThat(httpResponseStatusLine.toString()).isEqualTo("HTTP/1.1 200 OK\n");
    }
}