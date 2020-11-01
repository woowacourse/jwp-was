package http.header.cookie;

import exception.InvalidCookieException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookieTest {

    @DisplayName("HTTP Cookie가 옵션이 있고 규격에 맞을 때 HttpCookie 객체 생성")
    @Test
    void httpCookieWithOptionOfTest() {
        HttpCookie cookieWithOption = HttpCookie.of("logined", "true",
                                                    HttpCookieOption.of(HttpCookieOptionName.PATH, "/index" + ".html"));

        assertThat(cookieWithOption).isInstanceOf(HttpCookie.class);
    }

    @DisplayName("HTTP Cookie가 옵션이 없고 규격에 맞을 때 HttpCookie 객체 생성")
    @Test
    void httpCookieOfTest() {
        HttpCookie cookieWithoutOption = HttpCookie.of("logined", "false");

        assertThat(cookieWithoutOption).isInstanceOf(HttpCookie.class);
    }

    @DisplayName("HTTP Cookie의 이름이 유효하지 않을 때 InvalidCookieException 발생")
    @ParameterizedTest
    @ValueSource(strings = {"", "    "})
    void httpCookieOfNameExceptionTest(String value) {
        Assertions.assertThatThrownBy(() -> HttpCookie.of(value, "true"))
                .isInstanceOf(InvalidCookieException.class)
                .hasMessageStartingWith("처리할 수 없는 Cookie 값입니다! -> ");
    }

    @DisplayName("HTTP Cookie의 값이 유효하지 않을 때 InvalidCookieException 발생")
    @ParameterizedTest
    @ValueSource(strings = {"", "    "})
    void httpCookieOfValueExceptionTest(String value) {
        Assertions.assertThatThrownBy(() -> HttpCookie.of("logined", value))
                .isInstanceOf(InvalidCookieException.class)
                .hasMessageStartingWith("처리할 수 없는 Cookie 값입니다! -> ");
    }

    @DisplayName("옵션이 존재하는 HttpCookie가 정해둔 형태의 메세지로 잘 변환되는지 확인")
    @Test
    void toHttpMessageTest() {
        String cookieName = "logined";
        String cookieValue = "true";
        String cookieOptionValue = "/index.html";
        HttpCookie cookieWithOption = HttpCookie.of(cookieName, cookieValue,
                                                    HttpCookieOption.of(HttpCookieOptionName.PATH, cookieOptionValue));
        String actualMessage = cookieWithOption.toHttpMessage();

        String expectedMessage = cookieName + "=" + cookieValue + ";Path=" + cookieOptionValue;

        Assertions.assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @DisplayName("옵션이 존재하지 않는 HttpCookie가 정해둔 형태의 메세지로 잘 변환되는지 확인")
    @Test
    void toHttpMessageWithoutCookieOptionTest() {
        String cookieName = "logined";
        String cookieValue = "true";
        HttpCookie cookieWithOption = HttpCookie.of(cookieName, cookieValue);
        String actualMessage = cookieWithOption.toHttpMessage();

        String expectedMessage = cookieName + "=" + cookieValue;

        Assertions.assertThat(actualMessage).isEqualTo(expectedMessage);
    }
}