package http.common;

import http.parser.CookieParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {

    @Test
    @DisplayName("(key,value)인 쿠키 속성 가져오는 검사")
    void get_attribute_value() {
        Cookie cookie = CookieParser.parse("Cookie: logined=true; Path=/");

        assertThat(cookie.getAttributeValue("logined")).isEqualTo("true");
        assertThat(cookie.getAttributeValue("Path")).isEqualTo("/");
    }

    @Test
    @DisplayName("HttpOnly와 같이 (key,value)가 아닌 속성 검사")
    void get_Attribute_without_value() {
        Cookie cookie = CookieParser.parse("Cookie: logined=true; Path=/; HttpOnly");

        assertThat(cookie.hasContainsAttributeWithoutValue("HttpOnly")).isTrue();
    }

    @Test
    @DisplayName("쿠키 속성이 넣은 순서대로 나오는지 확인")
    void getCookieAttributeString() {
        Cookie cookie = CookieParser.parse("Cookie: logined=true; Path=/; HttpOnly");
        assertThat(cookie.getCookieAttributeString()).isEqualTo("logined=true; Path=/; HttpOnly");
    }

    @Test
    @DisplayName("Cookie Logined가 맞게 나오는지 검사")
    void isLogined() {
        Cookie cookie1 = CookieParser.parse("Cookie: logined=true; Path=/; HttpOnly");
        Cookie cookie2 = CookieParser.parse("Cookie: logined=false; Path=/; HttpOnly");

        assertThat(cookie1.isLogined()).isTrue();
        assertThat(cookie2.isLogined()).isFalse();
    }
}