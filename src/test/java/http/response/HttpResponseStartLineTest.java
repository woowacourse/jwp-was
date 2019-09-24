package http.response;

import http.request.HttpVersion;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseStartLineTest {

    @Test
    void convert_test() {
        HttpResponseStartLine httpResponseStartLine = new HttpResponseStartLine(StatusCode.OK, HttpVersion.HTTP_1_1);

        assertThat(httpResponseStartLine.convertLineToString()).isEqualTo("HTTP/1.1 200 OK");
    }
}
