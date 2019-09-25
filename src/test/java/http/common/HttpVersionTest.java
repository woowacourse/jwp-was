package http.common;

import http.common.exception.HttpVersionNotFoundException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpVersionTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "HTTP/0.8", "HTTP/3.1", "HTTP/4.0"})
    void 존재하지_않는_HttpVersion(String input) {
        assertThrows(HttpVersionNotFoundException.class, () -> HttpVersion.of(input));
    }
}