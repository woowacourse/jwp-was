package webserver.http;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpBodyTest {
    @DisplayName("현재 Body가 비었는 지 확인")
    @Test
    void isEmpty() {
        assertThat(new HttpBody("hello").isEmpty()).isFalse();
    }
}