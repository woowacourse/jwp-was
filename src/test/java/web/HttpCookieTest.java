package web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookieTest {

    @DisplayName("Map 형태의 쿠키를 String 형태로 변환한다.")
    @Test
    void convertToResponse() {
        HttpCookie httpCookie = new HttpCookie("");
        httpCookie.addCookie("session", "123");
        httpCookie.addCookie("key", "value");

        String converted = httpCookie.convertToResponse();

        assertThat(converted).isEqualTo("Set-Cookie: Path=/; session=123; key=value");
    }

    @DisplayName("쿠키 값이 하나만 들어온 경우")
    @Test
    void convertToResponse2() {
        HttpCookie httpCookie = new HttpCookie("session=123");

        Map<String, String> cookies = httpCookie.getCookies();

        assertThat(cookies.get("session")).isEqualTo("123");
    }

    @DisplayName("쿠키 값이 여러개 들어온 경우")
    @Test
    void convertToResponse3() {
        HttpCookie httpCookie = new HttpCookie("session=123; key=value");

        Map<String, String> cookies = httpCookie.getCookies();

        assertThat(cookies.get("session")).isEqualTo("123");
        assertThat(cookies.get("key")).isEqualTo("value");
    }

    @DisplayName("쿠키 값이 없는 경우 기본 Set-Cookie인 Path만 존재한다.")
    @Test
    void convertToResponse4() {
        HttpCookie httpCookie = new HttpCookie("");

        Map<String, String> cookies = httpCookie.getCookies();

        assertThat(cookies.get("Path")).isEqualTo("/");
        assertThat(cookies).hasSize(1);
    }
}