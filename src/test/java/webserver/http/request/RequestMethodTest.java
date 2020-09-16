package webserver.http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestMethodTest {
    @DisplayName("생성 테스트")
    @Test
    void of() {
        assertThat(RequestMethod.of("GET")).isEqualTo(RequestMethod.GET);
    }

    @DisplayName("생성 예외 테스트")
    @Test
    void ofException() {
        assertThatThrownBy(() -> RequestMethod.of("hi"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("허용되지 않는");
    }

    @DisplayName("Http Method가 body를 지원하는 지 확인한다.")
    @Test
    void allowBody() {
        assertThat(RequestMethod.GET.allowBody()).isFalse();
        assertThat(RequestMethod.POST.allowBody()).isTrue();
    }
}