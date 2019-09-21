package http.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseHeaderTest {
    private ResponseHeader responseHeader;

    @BeforeEach
    public void setUp() {
        responseHeader = ResponseHeader.of();
    }

    @Test
    public void putTest() {
        responseHeader.put("Content-Length", "1");

        assertThat(responseHeader.toString()).contains("Content-Length: 1");
    }
}