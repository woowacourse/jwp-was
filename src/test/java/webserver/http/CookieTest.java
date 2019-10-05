package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {
    @Test
    void toString_test() {
        Cookie cookie = new Cookie("JSESSIONID", "sessionId");
        cookie.setDomain("woowa.com");

        String expected = "name=JSESSIONID ;value=sessionId ;domain=woowa.com ;path=/ ;";
        assertThat(cookie.toString()).isEqualTo(expected);
    }
}