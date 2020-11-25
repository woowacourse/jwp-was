package http.response;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HttpResponseLineTest {

    @Test
    void init() {
        final HttpResponseLine httpResponseLine = new HttpResponseLine();

        assertThat(httpResponseLine.getVersion()).isEqualTo("HTTP/1.1");
        assertThat(httpResponseLine.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void setHttpStatus() {
        final HttpResponseLine httpResponseLine = new HttpResponseLine();
        httpResponseLine.setHttpStatus(HttpStatus.OK);

        assertThat(httpResponseLine.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }
}