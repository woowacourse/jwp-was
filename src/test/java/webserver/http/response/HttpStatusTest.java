package webserver.http.response;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.http.response.exception.InvalidCodeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpStatusTest {

    @ParameterizedTest
    @CsvSource(value = {"OK|200", "FOUND|302"}, delimiter = '|')
    void 유효코드_확인(HttpStatus httpStatus, int code) {
        assertEquals(httpStatus, HttpStatus.of(code));
    }

    @ParameterizedTest
    @CsvSource(value = {"999", "998", "77666"})
    void 유효하지_않은_코드_확인(int code) {
        assertThrows(InvalidCodeException.class, () -> {
            HttpStatus.of(code);
        });
    }
}