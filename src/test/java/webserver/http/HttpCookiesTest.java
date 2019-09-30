package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.headerfields.HttpCookie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpCookiesTest {
    @Test
    @DisplayName("정상적으로 HttpCookies를 생성한다.")
    void createHttpCookies() {
        HttpCookies httpCookies = new HttpCookies();

        assertThat(httpCookies).isEqualTo(new HttpCookies());
        assertThat(httpCookies.hashCode()).isEqualTo(new HttpCookies().hashCode());
    }

    @Test
    @DisplayName("HttpCookies에 정상적으로 쿠키를 저장한다.")
    void addCookie() {
        HttpCookies httpCookies = new HttpCookies();

        HttpCookie cookie = new HttpCookie();
        httpCookies.add(cookie);

        assertThat(httpCookies).isNotEqualTo(new HttpCookies());
    }

    @Test
    @DisplayName("모든 쿠키에 대해 Response 형식에 맞는 문자열로 변환한다.")
    void responseString() {
        HttpCookies httpCookies = new HttpCookies();

        HttpCookie cookie = new HttpCookie();
        cookie.sessionCookie("sessionId");
        httpCookies.add(cookie);

        assertTrue(httpCookies.responseString().get().length() > 0);
    }
}