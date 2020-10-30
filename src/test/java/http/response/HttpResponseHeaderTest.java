package http.response;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HttpResponseHeaderTest {

    @Test
    void addResponseHeader() {
        final HttpResponseHeader httpResponseHeader = new HttpResponseHeader();
        httpResponseHeader.addResponseHeader("Content-type", "text/html");

        assertThat(httpResponseHeader.getValue("Content-type")).isEqualTo("text/html");
    }
}