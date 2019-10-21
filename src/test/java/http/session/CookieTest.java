package http.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {
    @Test
    @DisplayName("Http request 헤더에서 쿠키 정보를 받아서 쿠키 객체를 만든다.")
    void parse() {
        String cookieString = "logined=true; Path=/";

        Cookie cookie = Cookie.parse(cookieString);

        assertThat(cookie.getAttribute("logined")).isEqualTo("true");
        assertThat(cookie.getAttribute("Path")).isEqualTo("/");
    }

    @Test
    @DisplayName("쿠키 속성을 추가한다.")
    void addCookie() {
        Cookie cookie = Cookie.getEmptyCookie();
        String key = "key";
        String value = "value";

        cookie.addAttribute(key, value);

        assertThat(cookie.getAttribute(key)).isEqualTo(value);
    }

    @Test
    @DisplayName("쿠키 헤더가 없는 경우 빈 쿠키를 만든다.")
    void returnEmptyCookie_ifCookieHeader_doesNotExist() {
        Cookie cookies = Cookie.parse(null);

        assertThat(cookies.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("쿠키 헤더는 있지만 속성 값이 하나도 없는 경우 빈 쿠키를 만든다.")
    void returnEmptyCookie_ifNoCookie_inCookieHeader() {
        Cookie cookies = Cookie.parse("");

        assertThat(cookies.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("속성의 이름이 없는 경우 해당 속성은 쿠키에 넣지 않는다.")
    void passParsing_ifCookieName_doesNotExist() {
        String cookiesInHeader = "=true; Path=/";

        Cookie cookie = Cookie.parse(cookiesInHeader);

        assertThat(cookie.getAttribute("logined")).isEqualTo(null);
        assertThat(cookie.getAttribute("Path")).isEqualTo("/");
    }

    @Test
    @DisplayName("속성의 값이 없는 경우 해당 속성은 쿠키에 넣지 않는다.")
    void passParsing_ifCookieValue_doesNotExist() {
        String cookiesInHeader = "logined=true; Path=";

        Cookie cookie = Cookie.parse(cookiesInHeader);

        assertThat(cookie.getAttribute("logined")).isEqualTo("true");
        assertThat(cookie.getAttribute("Path")).isEqualTo(null);
    }
}