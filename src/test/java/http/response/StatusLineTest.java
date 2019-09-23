package http.response;

import http.common.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusLineTest {
    @Test
    public void setHttpStatusTest() {
        StatusLine statusLine = StatusLine.of();
        statusLine.setHttpStatus(HttpStatus.NOT_FOUND);

        assertThat(statusLine.toString()).isEqualTo("HTTP/1.1 404 NOT FOUND");
    }
}