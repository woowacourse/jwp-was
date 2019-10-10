package webserver.domain;

import org.junit.jupiter.api.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {
    private static final String NAME = "name";
    private static final String VALUE = "value";

    @Test
    void testToString() {
        final Cookie cookie = new Cookie(NAME, VALUE);
        cookie.setMaxAge(300);
        cookie.setPath("/yeah");
        assertThat(cookie.toString()).isEqualTo("name=value; Max-Age=300; Path=/yeah");
    }

    @Test
    void setMaxAge() {
        final Cookie cookie = new Cookie(NAME, VALUE);
        cookie.setMaxAge(7000_0000);
        assertThat(cookie.getMaxAge()).isEqualTo(3153_6000);
    }

    @Test
    void expiresToString() {
        final Cookie cookie = new Cookie(NAME, VALUE);
        final ZonedDateTime time = ZonedDateTime.of(2020, 10, 24, 20, 15, 30, 0, ZoneOffset.UTC);
        cookie.setExpires(time);
        assertThat(cookie.toString()).isEqualTo("name=value; Expires=Sat, 24 Oct 2020 20:15:30 GMT; Path=/");
    }

    @Test
    void automatic_encode() {
        final Cookie cookie = new Cookie(NAME, "가나다abc");
        assertThat(cookie.toString()).isEqualTo("name=%EA%B0%80%EB%82%98%EB%8B%A4abc; Path=/");
    }
}