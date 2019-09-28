package webserver.message.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseCookieTest {

    @Test
    @DisplayName("쿠키 생성 테스트1")
    void create1() {
        ResponseCookie cookie = new ResponseCookie
                .Builder("sessionId", "1234")
                .path("/")
                .build();

        assertThat(cookie.toString()).isEqualTo("sessionId=1234; Path=/; ");
    }

    @Test
    @DisplayName("쿠키 생성 테스트2")
    void create2() {
        ResponseCookie cookie = new ResponseCookie
                .Builder("sessionId", "1234")
                .httpOnly()
                .build();

        assertThat(cookie.toString()).isEqualTo("sessionId=1234; HttpOnly; ");
    }

    @Test
    @DisplayName("쿠키 생성 테스트3")
    void create3() {
        ResponseCookie cookie = new ResponseCookie
                .Builder("sessionId", "1234")
                .path("/")
                .secure()
                .httpOnly()
                .build();

        assertThat(cookie.toString()).isEqualTo("sessionId=1234; Path=/; Secure; HttpOnly; ");
    }
}