package http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HttpUriTest {

    @Test
    void create_uri() {
        String uri = "/index.html";
        assertDoesNotThrow(() -> new HttpUri(uri));
    }
}