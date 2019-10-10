package webserver.domain;

import org.junit.jupiter.api.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CookiesTest {
    private static final String NAME = "name";
    private static final String VALUE = "value";

    @Test
    void parseString() {
        final ZonedDateTime time = ZonedDateTime.of(2020, 10, 24, 20, 15, 30, 0, ZoneOffset.UTC);
        final String rawCookies = "name=value; asdf=qwerty; Expires=Sat, 24 Oct 2020 20:15:30 GMT; Max-Age=123";
        final Cookies cookies = new Cookies(rawCookies);
        assertThat(cookies.getCookieValue(NAME)).isEqualTo(VALUE);
        assertThat(cookies.getCookie(NAME).getMaxAge()).isEqualTo(123);
        assertThat(cookies.getCookie("asdf").getExpires()).isEqualTo(time);
    }

    @Test
    void addCookie() {
        final Cookies cookies = new Cookies();
        final Cookie cookie = new Cookie(NAME, VALUE);
        cookies.addCookie(cookie);
        assertThat(cookies.getCookie(NAME)).isEqualTo(cookie);
    }

    @Test
    void setCookie() {
        final Cookies cookies = new Cookies();
        final Cookie cookie = new Cookie(NAME, VALUE);
        cookies.addCookie(cookie);
        cookie.setValue("1234");
        assertThat(cookies.getCookie(NAME).getValue()).isEqualTo("1234");
    }
}