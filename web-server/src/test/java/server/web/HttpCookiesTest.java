package server.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.web.cookie.CookieOption;
import server.web.cookie.HttpCookie;
import server.web.cookie.HttpCookies;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookiesTest {

    @DisplayName("List 형태의 쿠키를 String 형태로 변환한다.")
    @Test
    void convertToResponse() {
        HttpCookies httpCookies = new HttpCookies("");
        httpCookies.add("session", "123", CookieOption.PATH, "/");
        httpCookies.add("key", "value");

        String converted = httpCookies.convertToResponse();

        assertThat(converted).isEqualTo(
                "Set-Cookie: session=123; Path=/" + System.lineSeparator()
                        + "Set-Cookie: key=value" + System.lineSeparator());
    }

    @DisplayName("쿠키 값이 하나만 들어온 경우")
    @Test
    void convertToResponse2() {
        HttpCookies httpCookies = new HttpCookies("session=123");

        List<HttpCookie> cookies = httpCookies.getCookies();

        assertThat(cookies.get(0).write()).isEqualTo("Set-Cookie: session=123" + System.lineSeparator());
    }

    @DisplayName("쿠키 값이 여러개 들어온 경우")
    @Test
    void convertToResponse3() {
        HttpCookies httpCookies = new HttpCookies("session=123; key=value");

        List<HttpCookie> cookies = httpCookies.getCookies();

        assertThat(cookies.get(0).write()).isEqualTo("Set-Cookie: session=123" + System.lineSeparator());
        assertThat(cookies.get(1).write()).isEqualTo("Set-Cookie: key=value" + System.lineSeparator());
    }

}