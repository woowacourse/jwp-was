package webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

public class CookieTest {

    @DisplayName("Cookie 생성 - 성공")
    @Test
    void constructor() {
        Cookie cookie = new Cookie("bingbong", "wooteco");
        cookie.setPath("/");

        assertAll(
            () -> assertThat(cookie.getName()).isEqualTo("bingbong"),
            () -> assertThat(cookie.getValue()).isEqualTo("wooteco"),
            () -> assertThat(cookie.getPath()).isEqualTo("/"),
            () -> assertThat(cookie.getMaxAge()).isEqualTo(-1),
            () -> assertThat(cookie.isHttpOnly()).isEqualTo(false)
        );
    }

    @DisplayName("Cookie 생성 - 예외, 이름이 null 또는 길이가 0인 경우")
    @ParameterizedTest
    @NullAndEmptySource
    void constructor_NameIsNullOrNameLengthIsZero_ThrownException(String input) {
        assertThatThrownBy(() -> new Cookie(input, "wooteco"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Cookie generateHeader - 성공, name과 value만 주는 경우")
    @Test
    void generateHeader_SetNameAndValue_Success() {
        Cookie cookie = new Cookie("name", "bingbong");

        assertThat(cookie.generateHeader()).isEqualTo("name=bingbong");
    }

    @DisplayName("Cookie generateHeader - 성공, 전부 set 해주는 경우")
    @Test
    void generateHeader_SetAll_Success() {
        Cookie cookie = new Cookie("name", "bingbong");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);

        assertThat(cookie.generateHeader())
            .isEqualTo("name=bingbong; Path=/; max-age=0; HttpOnly");
    }

    @DisplayName("Cookie 파싱")
    @Test
    void parse() {
        String cookieHeader = "logined=true; JSESSIONID=hello";

        List<Cookie> cookies = CookieUtils.parse(cookieHeader);

        assertAll(
            () -> assertThat(cookies).hasSize(2),
            () -> assertThat(cookies.get(0).getName()).isEqualTo("logined"),
            () -> assertThat(cookies.get(0).getValue()).isEqualTo("true")
        );
    }
}
