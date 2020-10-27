package web.server.domain.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.common.Cookie;
import web.server.domain.exception.CookieNotFoundException;

class CookiesTest {

    @DisplayName("쿠키를 등록한다.")
    @Test
    void from() {
        Cookies cookies = Cookies.from("foo=bar;");

        assertAll(
            () -> assertThat(cookies.getCookies()).hasSize(1),
            () -> assertThat(cookies.getCookies().get("foo").getValue()).isEqualTo("bar")
        );
    }

    @DisplayName("여러개의 쿠키를 등록하고 쿠키를 꺼낸다.")
    @Test
    void findCookie() {
        Cookies cookies = Cookies.from("foo=bar; foo2=bar2; foo3=bar3");
        Cookie cookie = cookies.findCookie("foo");

        assertAll(
            () -> assertThat(cookie.getKey()).isEqualTo("foo"),
            () -> assertThat(cookie.getValue()).isEqualTo("bar")
        );
    }

    @DisplayName("존재하지 않는 쿠키를 꺼내면 예외가 발생한다.")
    @Test
    void name() {
        Cookies cookies = Cookies.from("foo=bar; foo2=bar2; foo3=bar3");
        assertThatThrownBy(
            () -> cookies.findCookie("pobi")
        ).isInstanceOf(CookieNotFoundException.class);
    }

    @Test
    void testFindCookie() {
    }
}