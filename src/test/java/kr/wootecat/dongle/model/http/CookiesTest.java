package kr.wootecat.dongle.model.http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import kr.wootecat.dongle.model.http.exception.IllegalRequestDataFormatException;

class CookiesTest {

    @DisplayName("요청 헤더의 Cookie 값은 '; ', 각 값들은 '='를 기준으로 경계를 가지고 있으며, 해당 형식의 문자열로 들어온 입력값을 Cookie 객체로 변환햔다.")
    @Test
    void toCookie_when_request_cookie_value_format_is_valid() {
        Cookies actual = Cookies.from("logined=true; JSESSIONID =123321123; ide= intellij ;etc=");
        assertAll(
                () -> assertThat(actual.getValue("logined")).isEqualTo("true"),
                () -> assertThat(actual.getValue("JSESSIONID")).isEqualTo("123321123"),
                () -> assertThat(actual.getValue("ide")).isEqualTo("intellij"),
                () -> assertThat(actual.getValue("etc")).isEqualTo("")
        );
    }

    @DisplayName("요청 헤더의 Cookie 값은 '; ', 각 값들은 '='를 기준으로 경계를 가지고 있어야한다. "
            + "해당 형식의 규칙을 가지고 있지 않는 문자열 값은 쿠키값 변환에 실패한 뒤, IllegalCookieFormatException을 던진다.")
    @ParameterizedTest
    @CsvSource(value = {"logined=true;", "logined==true", "logined&false", "logined=true ;; JSESSIONID=1"})
    void toCookie_throw_IllegalCookieFormatException_when_request_cookie_format_is_not_valid(
            String requestCookieValue) {
        assertThatThrownBy(() -> Cookies.from(requestCookieValue))
                .isInstanceOf(IllegalRequestDataFormatException.class)
                .hasMessageContaining("파싱할 수 없는 쿠키값 형식으로 요청을 받았습니다.");

    }
}