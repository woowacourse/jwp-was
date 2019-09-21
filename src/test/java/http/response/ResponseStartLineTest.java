package http.response;

import http.common.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseStartLineTest {
    @Test
    public void setHttpStatusTest() {
        ResponseStartLine responseStartLine = ResponseStartLine.of();
        responseStartLine.setHttpStatus(HttpStatus.NOT_FOUND);

        assertThat(responseStartLine.toString()).isEqualTo("HTTP/1.1 404 NOT FOUND");
    }
}