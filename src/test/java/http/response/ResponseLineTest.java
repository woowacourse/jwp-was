package http.response;

import http.common.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseLineTest {
    @Test
    public void setHttpStatusTest() {
        ResponseLine responseLine = ResponseLine.of();
        responseLine.setHttpStatus(HttpStatus.NOT_FOUND);

        assertThat(responseLine.toString()).isEqualTo("HTTP/1.1 404 NOT FOUND");
    }
}