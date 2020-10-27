package http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CookieTest {
    @Test
    void create() {
        String cookieString = "session_id=1234; logined=true";

        Cookie cookie = new Cookie(cookieString);

        assertAll(
                () -> assertThat(cookie.get("session_id")).isEqualTo("1234"),
                () -> assertThat(cookie.get("logined")).isEqualTo("true")
        );
    }
}
