package http.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookieTest {
    @Test
    @DisplayName("Http request 헤더에서 쿠키 정보를 받아서 쿠키 객체를 만든다.")
    void parse() {
        String cookiesInHeader = "logined=true; Path=/";

        HttpCookie cookies = HttpCookie.parse(cookiesInHeader);

        assertThat(cookies.getCookieValue("logined")).isEqualTo("true");
        assertThat(cookies.getCookieValue("Path")).isEqualTo("/");
    }

    @Test
    @DisplayName("쿠키 헤더가 없는 경우 빈 쿠키를 만든다.")
    void returnEmptyCookie_ifCookieHeader_doesNotExsist() {
        HttpCookie cookies = HttpCookie.parse(null);

        assertThat(cookies.getCookies()).isEmpty();
    }

    @Test
    @DisplayName("쿠키 헤더는 있지만 쿠키가 하나도 없는 경우 빈 쿠키를 만든다.")
    void returnEmptyCookie_ifNoCookie_inCookieHeader() {
        HttpCookie cookies = HttpCookie.parse("");

        assertThat(cookies.getCookies()).isEmpty();
    }

    @Test
    @DisplayName("쿠키 이름이 없는 경우 해당 쿠키는 만들지 않는다.")
    void passParsing_ifCookieName_doesNotExist() {
        String cookiesInHeader = "=true; Path=/";

        HttpCookie cookies = HttpCookie.parse(cookiesInHeader);

        assertThat(cookies.getCookieValue("logined")).isEqualTo(null);
        assertThat(cookies.getCookieValue("Path")).isEqualTo("/");
    }

    @Test
    @DisplayName("쿠키 값이 없는 경우 해당 쿠키는 만들지 않는다.")
    void passParsing_ifCookieValue_doesNotExist() {
        String cookiesInHeader = "logined=true; Path=";

        HttpCookie cookies = HttpCookie.parse(cookiesInHeader);

        assertThat(cookies.getCookieValue("logined")).isEqualTo("true");
        assertThat(cookies.getCookieValue("Path")).isEqualTo(null);
    }
}