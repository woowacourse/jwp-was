package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpVersionTest {
    @Test
    @DisplayName("소수점을 입력받아 HttpVersion 객체를 정상적으로 생성한다.")
    void createHttpVersion() {
        double version = 1.1;

        assertDoesNotThrow(() -> HttpVersion.of(version)
                .orElseThrow(IllegalArgumentException::new)
        );
    }

    @Test
    @DisplayName("문자열로 입력받아 HttpVersion 객체를 정상적으로 생성한다.")
    void createHttpVersion2() {
        String version = "HTTP/1.1";

        assertDoesNotThrow(() -> HttpVersion.of(version)
                .orElseThrow(IllegalArgumentException::new)
        );
    }

    @Test
    @DisplayName("없는 버전을 입력받아 HttpVersion 객체 생성을 실패한다.")
    void createFailHttpVersion() {
        String version = "HTTP/3.0";

        assertThrows(IllegalArgumentException.class, () ->
                HttpVersion.of(version).orElseThrow(IllegalArgumentException::new)
        );
    }

    @Test
    @DisplayName("잘못된 입력 값을 입력받아 HttpVersion 객체 생성을 실패한다.")
    void createFailHttpVersion2() {
        String version = "1.1";

        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
                HttpVersion.of(version)
        );
    }
}