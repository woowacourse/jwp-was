package webserver.http.common;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpVersionTest {
    @ParameterizedTest
    @CsvSource(value = {"HTTP_1_0|HTTP/1.0", "HTTP_1_1|HTTP/1.1", "HTTP_2_0|HTTP/2.0"}, delimiter = '|')
    void 네트워크_버전_이름_확인(HttpVersion httpVersion, String name) {
        assertEquals(httpVersion, HttpVersion.of(name));
    }
}