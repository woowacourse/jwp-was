package http.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseStartLineTest {

    @Test
    void toString_test() {
        HttpResponseStartLine httpResponseStartLine = new HttpResponseStartLine(StatusCode.OK, "HTTP/1.1");

        assertThat(httpResponseStartLine.toString()).isEqualTo("HTTP/1.1 200 OK");
    }
}
