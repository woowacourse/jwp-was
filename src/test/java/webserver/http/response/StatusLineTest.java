package webserver.http.response;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.http.common.HttpVersion;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class StatusLineTest {

    @ParameterizedTest
    @CsvSource(value = {"HTTP_1_1", "HTTP_2_0"})
    void 생성(HttpVersion httpVersion) {
        assertDoesNotThrow(() ->
                new StatusLine(httpVersion)
        );
    }
}