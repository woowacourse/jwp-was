package request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestCookiesTest {

    @Test
    @DisplayName("from, getValue happy case")
    void fromAndGet() {
        RequestCookies cookies = RequestCookies.from("flavor=mint; price=5000");

        assertThat(cookies.getValue("flavor")).isEqualTo("mint");
        assertThat(cookies.getValue("price")).isEqualTo("5000");
    }

    @Test
    @DisplayName("from - cookies format 입력이 이상할 때 예외처리")
    void from_IfFormatIsWrong_ThrowException() {
        assertThatThrownBy(() -> RequestCookies.from("flavor:mint; price=5000"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("getValue - 찾으려는 쿠키가 존재하지 않는 경우 예외처리")
    void getValue_IfCookieNameIsNotExist_ThrowException() {
        RequestCookies cookies = RequestCookies.from("flavor=mint; price=5000");

        assertThatThrownBy(() -> cookies.getValue("sessionId"))
            .isInstanceOf(IllegalArgumentException.class);
    }
}