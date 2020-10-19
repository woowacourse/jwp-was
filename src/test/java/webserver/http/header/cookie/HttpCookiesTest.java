package webserver.http.header.cookie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookiesTest {

    @DisplayName("HTTP Cookie가 규격에 맞을 때 HttpCookies 객체 생성")
    @ParameterizedTest
    @ValueSource(strings = {"logined=false", "sessionId=abc123; logined=true", "sessionId=abc123; logined=true; " +
            "id=coollime"})
    void httpCookiesFromTest(String cookieValue) {
        assertThat(HttpCookies.from(cookieValue)).isNotNull()
                .isInstanceOf(HttpCookies.class);
    }

    @DisplayName("HttpCookies에 존재하는 Cookie 값을 요청하면 정상적으로 반환")
    @ParameterizedTest
    @CsvSource(value = {"id=abc;email=a@abc.net, id", "sessionId=abc123, sessionId", "sessionId=abc123;logined=true, " +
            "logined"})
    void getCookieValueTest(String cookie, String cookieName) {
        HttpCookies httpCookies = HttpCookies.from(cookie);

        assertThat(httpCookies.getCookieValue(cookieName)).isPresent();
    }

    @DisplayName("HttpCookies에 존재하지 않는 Cookie 값을 요청하면 null 반환")
    @ParameterizedTest
    @CsvSource(value = {"id=abc;email=a@abc.net, password", "sessionId=abc123, session", "sessionId=abc123;" +
            "logined=true, id"})
    void getCookieValueIsNullTest(String cookie, String notExistCookieName) {
        HttpCookies httpCookies = HttpCookies.from(cookie);

        assertThat(httpCookies.getCookieValue(notExistCookieName)).isNotPresent();
    }
}