package webserver.http.response;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.http.common.HttpVersion;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class StartLineTest {

    @ParameterizedTest
    @CsvSource(value = {"HTTP_1_1|OK", "HTTP_2_0|FOUND"}, delimiter = '|')
    void 생성(HttpVersion httpVersion, HttpStatus httpStatus) {
        assertDoesNotThrow(() ->
                new StartLine(httpVersion, httpStatus)
        );
    }
}