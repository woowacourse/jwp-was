package kr.wootecat.dongle.http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import kr.wootecat.dongle.http.exception.IllegalRequestDataFormatException;

class CookieParserTest {

    @DisplayName("쿠키 인스턴스를 http header 쿠키 값 형식으로 변환한다.")
    @Test
    void normal_cookie_instance_parse_to_response_string() {
        Cookie cookie = new Cookie("logined", true, "/");
        String actual = CookieParser.toHttpResponseFormat(cookie);
        assertThat(actual).isEqualTo("logined=true; Path=/");
    }

    @DisplayName("요청 헤더의 Cookie 값은 '; ', 각 값들은 '='를 기준으로 경계를 가지고 있으며, 해당 형식의 문자열로 들어온 입력값을 Cookie 객체로 변환햔다.")
    @Test
    void toCookie_when_request_cookie_value_format_is_valid() {
        List<Cookie> actual = CookieParser.toCookie("logined=true; JSESSIONID =123321123; ide= intellij ;etc=");
        assertAll(
                () -> assertThat(actual.get(0).getName()).isEqualTo("logined"),
                () -> assertThat(actual.get(0).getValue()).isEqualTo("true"),

                () -> assertThat(actual.get(1).getName()).isEqualTo("JSESSIONID"),
                () -> assertThat(actual.get(1).getValue()).isEqualTo("123321123"),

                () -> assertThat(actual.get(2).getName()).isEqualTo("ide"),
                () -> assertThat(actual.get(2).getValue()).isEqualTo("intellij"),

                () -> assertThat(actual.get(3).getName()).isEqualTo("etc"),
                () -> assertThat(actual.get(3).getValue()).isEqualTo("")
        );
    }

    @DisplayName("요청 헤더의 Cookie 값은 '; ', 각 값들은 '='를 기준으로 경계를 가지고 있어야한다. "
            + "해당 형식의 규칙을 가지고 있지 않는 문자열 값은 쿠키값 변환에 실패한 뒤, IllegalCookieFormatException을 던진다.")
    @ParameterizedTest
    @CsvSource(value = {"logined=true;", "logined==true", "logined&false", "logined=true ;; JSESSIONID=1"})
    void toCookie_throw_IllegalCookieFormatException_when_request_cookie_format_is_not_valid(
            String requestCookieValue) {
        assertThatThrownBy(() -> CookieParser.toCookie(requestCookieValue))
                .isInstanceOf(IllegalRequestDataFormatException.class)
                .hasMessageContaining("파싱할 수 없는 쿠키값 형식으로 요청을 받았습니다.");

    }
}