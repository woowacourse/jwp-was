package webserver.http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpMethodTest {
    @DisplayName("생성 테스트")
    @Test
    void of() {
        assertThat(HttpMethod.of("GET")).isEqualTo(HttpMethod.GET);
    }

    @DisplayName("생성 예외 테스트")
    @Test
    void ofException() {
        assertThatThrownBy(() -> HttpMethod.of("hi"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("허용되지 않는");
    }

    @DisplayName("Http Method가 body를 지원하는 지 확인한다.")
    @Test
    void allowBody() {
        assertThat(HttpMethod.GET.allowBody()).isFalse();
        assertThat(HttpMethod.POST.allowBody()).isTrue();
    }
}