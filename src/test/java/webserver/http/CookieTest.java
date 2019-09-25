package webserver.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {

    @Test
    void 쿠키_정보_문자열_확인() {
        // given
        final Cookie cookie = new Cookie("name" , "value");
        cookie.setMaxAge(12);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        final String actual = "name=value; max-age=12; Path=/; Secure; HttpOnly";

        // when
        final String expected = cookie.parseInfoAsString();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}