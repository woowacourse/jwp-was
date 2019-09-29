package webserver.http.request;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import was.exception.MethodNotAllowedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpMethodTest {

    @ParameterizedTest
    @CsvSource(value = {"GET|GET", "POST|POST", "PUT|PUT", "DELETE|DELETE"}, delimiter = '|')
    void 지원하는_메서드(HttpMethod httpMethod, String method) {
        assertEquals(httpMethod, HttpMethod.of(method));
    }

    @ParameterizedTest
    @ValueSource(strings = {"FETCH", "HEADER", "TRACE", "OPTION"})
    void 지원하지_않는_메서드(String method) {
        assertThrows(MethodNotAllowedException.class, () -> {
            HttpMethod.of(method);
        });
    }
}