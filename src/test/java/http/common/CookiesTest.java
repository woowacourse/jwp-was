package http.common;

import http.parser.CookieParser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookiesTest {

    @Test
    void addCookie() {
        Cookie cookie1 = CookieParser.parse("Set-Cookie: logined=true; HttpOnly; Path=/");
        Cookie cookie2 = CookieParser.parse("Set-Cookie: logined=false; Path=/");

        Cookies cookies = new Cookies();
        cookies.addCookie(cookie1);
        cookies.addCookie(cookie2);

        assertThat(cookies.getCookie(0)).isEqualTo(cookie1);
        assertThat(cookies.getCookie(1)).isEqualTo(cookie2);
    }
}