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

    @Test
    void keySet() {
        final HttpResponseHeader httpResponseHeader = new HttpResponseHeader();
        httpResponseHeader.addResponseHeader("Content-type", "text/html");
        httpResponseHeader.addResponseHeader("Content-Length", 7000);

        assertThat(httpResponseHeader.keySet()).hasSize(2);
        assertThat(httpResponseHeader.keySet().contains("Content-type")).isTrue();
        assertThat(httpResponseHeader.keySet().contains("Content-Length")).isTrue();
    }
}