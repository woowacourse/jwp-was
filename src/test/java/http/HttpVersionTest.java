package http;

import http.exception.HttpVersionMismatchException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpVersionTest {
    @Test
    @DisplayName("해당 버전을 지원하지 않는 경우")
    void notSupport() {
        assertThatThrownBy(() -> HttpVersion.resolve("mismatch")).isInstanceOf(HttpVersionMismatchException.class);
    }

    @Test
    @DisplayName("HTTP/1.0 버전 확인")
    void checkHttp_1_0() {
        assertThat(HttpVersion.resolve("HTTP/1.0")).isEqualTo(HttpVersion.HTTP_1_0);
    }

    @Test
    @DisplayName("HTTP/1.1 버전 확인")
    void checkHttp_1_1() {
        assertThat(HttpVersion.resolve("HTTP/1.1")).isEqualTo(HttpVersion.HTTP_1_1);
    }

    @Test
    @DisplayName("HTTP/2.0 버전 확인")
    void checkHttp_2_0() {
        assertThat(HttpVersion.resolve("HTTP/2.0")).isEqualTo(HttpVersion.HTTP_2_0);
    }
}