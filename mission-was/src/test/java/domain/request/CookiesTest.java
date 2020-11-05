package domain.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import servlet.Cookie;

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
        Cookie cookie = cookies.findCookie("foo").get();

        assertAll(
            () -> assertThat(cookie.getKey()).isEqualTo("foo"),
            () -> assertThat(cookie.getValue()).isEqualTo("bar")
        );
    }

    @DisplayName("등록된 쿠키를 검색한다.")
    @Test
    void testFindCookie() {
        Cookies cookies = Cookies.from("foo=bar; foo2=bar2; foo3=bar3");
        Cookie cookie = cookies.findCookie("foo").get();
        assertAll(
            () -> assertThat(cookie.getKey()).isEqualTo("foo"),
            () -> assertThat(cookie.getValue()).isEqualTo("bar")
        );
    }

    @DisplayName("중복된 쿠키이름은 마지막 쿠키만 허용한다.")
    @Test
    void testFrom() {
        Cookies cookies = Cookies.from("foo=bar; foo=bar2; foo=bar3");

        assertAll(
            () -> assertThat(cookies.getCookies()).hasSize(1),
            () -> assertThat(cookies.findCookie("foo").get().getValue()).isEqualTo("bar3")
        );
    }
}