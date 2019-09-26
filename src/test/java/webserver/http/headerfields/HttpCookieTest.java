package webserver.http.headerfields;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookieTest {

    @Test
    @DisplayName("HttpCookie를 정상적으로 생성한다.")
    void createHttpCookie() {
        assertThat(new HttpCookie()).isEqualTo(new HttpCookie());
    }

    @Test
    @DisplayName("로그인 상태의 HttpCookie를 정상적으로 설정한다.")
    void createLoginHttpCookie() {
        HttpCookie cookie = new HttpCookie();
        HttpCookie compareCookie = new HttpCookie();

        cookie.loginCookie(true, "/");

        assertThat(cookie).isNotEqualTo(compareCookie);
        compareCookie.loginCookie(true, "/");
        assertThat(cookie).isEqualTo(compareCookie);
    }

    @Test
    @DisplayName("비로그인 상태의 HttpCookie를 정상적으로 설정한다.")
    void createNotLoginHttpCookie() {
        HttpCookie cookie = new HttpCookie();
        HttpCookie compareCookie = new HttpCookie();

        cookie.loginCookie(false, "/");
        compareCookie.loginCookie(true, "/");

        assertThat(cookie).isNotEqualTo(compareCookie);
        compareCookie.loginCookie(false, "/");
        assertThat(cookie).isEqualTo(compareCookie);
    }

    @Test
    @DisplayName("로그인 상태의 HttpCookie의 값을 한 줄로 나타낸다.")
    void loginCookieLine() {
        HttpCookie cookie = new HttpCookie();
        cookie.loginCookie(true, "/");
        assertThat(cookie.line()).isEqualTo("logined=true; Path=/");
    }

    @Test
    @DisplayName("비로그인 상태의 HttpCookie의 값을 한 줄로 나타낸다.")
    void notLoginCookieLine() {
        HttpCookie cookie = new HttpCookie();
        cookie.loginCookie(false, "/");
        assertThat(cookie.line()).isEqualTo("logined=false; Path=/");
    }
}