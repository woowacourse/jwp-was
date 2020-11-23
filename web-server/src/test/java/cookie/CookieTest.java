package cookie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CookieTest {

    @Test
    @DisplayName("from")
    void from() {
        Cookie cookie = Cookie.from("flavor=chocolate");

        assertThat(cookie.getName()).isEqualTo("flavor");
        assertThat(cookie.getValue()).isEqualTo("chocolate");
    }

    @Test
    @DisplayName("from - 쿠키 형식을 따르지 않은 문자열 입력시 예외처리")
    void from_IfArgumentStringIsWeird_ThrowException() {
        assertThatThrownBy(() -> Cookie.from("flavor:chocolate"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void toFormat() {
        Cookie cookie = new Cookie("flavor", "chocolate");

        assertThat(cookie.toFormat()).isEqualTo("flavor=chocolate");
    }
}
