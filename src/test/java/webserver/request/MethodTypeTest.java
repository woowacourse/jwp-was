package webserver.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import exception.UnsupportedMethodTypeException;

class MethodTypeTest {
    @DisplayName("정상적인 MethodType을 생성한다.")
    @Test
    void post() {
        MethodType post = MethodType.of("post");

        assertThat(post).isEqualTo(MethodType.POST);
    }

    @DisplayName("정상적이지 않은 MethodType을 생성하면 예외를 반환한다.")
    @Test
    void get() {
        assertThatThrownBy(() -> MethodType.of("dd"))
            .isInstanceOf(UnsupportedMethodTypeException.class)
            .hasMessage("dd는 지원하지 않는 메소드 타입입니다.");
    }
}