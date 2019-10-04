package http.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {
    @Test
    public void toStringTest() {
        Cookie cookie = new Cookie("logined", "true");
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setPath("/");

        assertThat(cookie.toString()).isEqualTo("logined=true; Max-Age=86400; Path=/");
    }
}