package http.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {
    @Test
    @DisplayName("Http request 헤더에서 쿠키 정보를 받아서 쿠키 객체를 만든다.")
    void parse() {
        String cookiesInHeader = "logined=true; Path=/";

        Cookie cookies = Cookie.parse(cookiesInHeader);

        assertThat(cookies.getAttribute("logined")).isEqualTo("true");
        assertThat(cookies.getAttribute("Path")).isEqualTo("/");
    }

    @Test
    @DisplayName("쿠키 헤더가 없는 경우 빈 쿠키를 만든다.")
    void returnEmptyCookie_ifCookieHeader_doesNotExsist() {
        Cookie cookies = Cookie.parse(null);

        assertThat(cookies.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("쿠키 헤더는 있지만 쿠키가 하나도 없는 경우 빈 쿠키를 만든다.")
    void returnEmptyCookie_ifNoCookie_inCookieHeader() {
        Cookie cookies = Cookie.parse("");

        assertThat(cookies.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("쿠키 이름이 없는 경우 해당 쿠키는 만들지 않는다.")
    void passParsing_ifCookieName_doesNotExist() {
        String cookiesInHeader = "=true; Path=/";

        Cookie cookies = Cookie.parse(cookiesInHeader);

        assertThat(cookies.getAttribute("logined")).isEqualTo(null);
        assertThat(cookies.getAttribute("Path")).isEqualTo("/");
    }

    @Test
    @DisplayName("쿠키 값이 없는 경우 해당 쿠키는 만들지 않는다.")
    void passParsing_ifCookieValue_doesNotExist() {
        String cookiesInHeader = "logined=true; Path=";

        Cookie cookies = Cookie.parse(cookiesInHeader);

        assertThat(cookies.getAttribute("logined")).isEqualTo("true");
        assertThat(cookies.getAttribute("Path")).isEqualTo(null);
    }
}