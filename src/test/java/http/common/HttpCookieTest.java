package http.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookieTest {
    @Test
    @DisplayName("Cookie 생성 테스트")
    public void create() {
        HttpCookie httpCookie = HttpCookie.of("logined=true; id=aiden");

        assertThat(httpCookie.getCookie("logined")).isEqualTo("true");
        assertThat(httpCookie.getCookie("id")).isEqualTo("aiden");
    }
}