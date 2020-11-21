package webserver.http;

import static org.assertj.core.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpVersionTest {

    @DisplayName("입력한 String 에 해당하는 HttpVersion 반환")
    @Test
    void of() {
        String version = "HTTP/1.1";
        assertThat(HttpVersion.of(version)).isEqualTo(HttpVersion.HTTP_1_1);
    }

    @DisplayName("입력한 String 에 해당하는 HttpVersion 이 없는 경우 예외 발생")
    @Test
    void of_Exception() {
        String version = "HTTP/1.9";
        assertThatThrownBy(() -> HttpVersion.of(version))
                .isInstanceOf(NoSuchElementException.class);
    }
}