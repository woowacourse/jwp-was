package http.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusLineTest {
    private StatusLine statusLine;

    @BeforeEach
    public void setUp() {
        statusLine = new StatusLine("HTTP/1.1");
        statusLine.setStatusCode(200);
        statusLine.setReasonPhrase("OK");
    }

    @Test
    public void getHttpVersion() {
        assertThat(statusLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    public void getStatusCode() {
        assertThat(statusLine.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void getReasonPhrase() {
        assertThat(statusLine.getReasonPhrase()).isEqualTo("OK");
    }

    @Test
    public void isOk() {
        assertThat(statusLine.isOk()).isTrue();
    }
}