package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

public class CookieTest {

    @DisplayName("Cookie 생성 - 성공")
    @Test
    void constructor() {
        Cookie cookie = new Cookie("bingbong", "wooteco");
        assertThat(cookie.getName()).isEqualTo("bingbong");
        assertThat(cookie.getValue()).isEqualTo("wooteco");
        assertThat(cookie.getMaxAge()).isEqualTo(-1);
        assertThat(cookie.isHttpOnly()).isEqualTo(false);
    }

    @DisplayName("Cookie 생성 - 예외, 이름이 null 또는 길이가 0인 경우")
    @ParameterizedTest
    @NullAndEmptySource
    void constructor_NameIsNullOrNameLengthIsZero(String input) {
        assertThatThrownBy(() -> new Cookie(input, "wooteco"))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
