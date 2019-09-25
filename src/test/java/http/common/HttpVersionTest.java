package http.common;

import http.exception.IllegalHttpVersionException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpVersionTest {
    @ParameterizedTest
    @CsvSource(value = {"HTTP_1_0|HTTP/1.0", "HTTP_1_1|HTTP/1.1", "HTTP_2_0|HTTP/2.0"}, delimiter = '|')
    void 네트워크_버전_이름_확인(HttpVersion httpVersion, String name) {
        assertEquals(httpVersion, HttpVersion.of(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"HTTP/0.1", "TCP", "ICMP"})
    void 잘못된_HTTP_버전을_받는_경우_IllegalHttpVersionException_발생_테스트(String version) {
        assertThrows(IllegalHttpVersionException.class, () -> HttpVersion.of(version));
    }
}