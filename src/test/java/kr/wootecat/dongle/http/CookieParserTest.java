package kr.wootecat.dongle.http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CookieParserTest {

    @DisplayName("쿠키 인스턴스를 http header 쿠키 값 형식으로 변환한다.")
    @Test
    void parse() {
        Cookie cookie = new Cookie("logined", true, "/");
        String actual = CookieParser.parse(cookie);
        assertThat(actual).isEqualTo("logined=true; Path=/");
    }

    @DisplayName("http request header 의 쿠키 문자열 값을 Cookie List 인스턴스로 변환한다.")
    @Test
    void toCookie() {
        List<Cookie> actual = CookieParser.toCookie("logined=true; JSESSIONID=123321123; ide=intellij");
        assertAll(
                () -> assertThat(actual.get(0).getName()).isEqualTo("logined"),
                () -> assertThat(actual.get(0).getValue()).isEqualTo("true"),
                () -> assertThat(actual.get(1).getName()).isEqualTo("JSESSIONID"),
                () -> assertThat(actual.get(1).getValue()).isEqualTo("123321123"),
                () -> assertThat(actual.get(2).getName()).isEqualTo("ide"),
                () -> assertThat(actual.get(2).getValue()).isEqualTo("intellij")
        );
    }
}