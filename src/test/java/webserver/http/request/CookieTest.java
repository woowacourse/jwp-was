package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {
    String sessionId = "123e4567-e89b-12d3-a456-426655440000";

    @DisplayName("쿠키 생성")
    @Test
    void createCookie_multiValueCookie_equal() {
        Cookie cookie = new Cookie("name=value; name2=value2; name3=value3");
        assertThat(cookie.get("name")).isEqualTo("value");
        assertThat(cookie.get("name2")).isEqualTo("value2");
        assertThat(cookie.get("name3")).isEqualTo("value3");
    }

    @DisplayName("쿠키 빈라인 일 때 빈 쿠키 생성")
    @Test
    void createCookie_nullLine_emptyMap(){
        Cookie cookie = new Cookie(null);
        assertThat(cookie.getCookies()).isEmpty();
    }

    @DisplayName("쿠키에서 세션 얻기")
    @Test
    void getSessionId_sessionIncludeCookie_equal() {
        Cookie cookie = new Cookie(Cookie.SESSION_ID_KEY + "=" + sessionId + "; name2=value2; name3=value3");
        assertThat(cookie.getSessionId()).isEqualTo(sessionId);
    }
}