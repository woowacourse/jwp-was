package webserver.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseHeadersTest {
    @Test
    void putAndGet() {
        ResponseHeaders responseHeaders = new ResponseHeaders();
        String key = "Content-Type";
        String value = "text/html";
        responseHeaders.put(key, value);
        assertThat(responseHeaders.get(key)).isEqualTo(value);
    }
}