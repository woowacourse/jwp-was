package jwp.was.webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jwp.was.webserver.exception.NotExistsHttpMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpMethodTest {

    @DisplayName("정적 팩터리 생성자, 성공")
    @Test
    void from_ExistsHttpMethod_Success() {
        HttpMethod httpMethodGet = HttpMethod.from("GET");

        assertThat(httpMethodGet).isEqualTo(HttpMethod.GET);
    }

    @DisplayName("정적 팩터리 생성자, 예외 발생 존재하지 않는 HttpMethod")
    @Test
    void from_NotExistsHttpMethod_ThrownException() {
        assertThatThrownBy(() -> HttpMethod.from("XXX"))
            .isInstanceOf(NotExistsHttpMethod.class);
    }

    @DisplayName("같은 문자열, True")
    @Test
    void isSame_SameString_True() {
        assertThat(HttpMethod.GET.isSame("GET")).isTrue();
    }

    @DisplayName("다른 대/소문자, False")
    @Test
    void isSame_DifferentCase_False() {
        assertThat(HttpMethod.GET.isSame("Get")).isFalse();
    }

    @DisplayName("다른 문자열, False")
    @Test
    void isSame_DifferentString_False() {
        assertThat(HttpMethod.GET.isSame("XXX")).isFalse();
    }
}
