package http.cookie;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookiesTest {
    @Test
    void getCookieTest() {
        Cookie cookie = new Cookie.Builder("test", "test value").build();

        Cookies cookies = new Cookies();
        cookies.add(cookie);

        assertThat(cookies.getCookie(cookie.getName())).isEqualTo(cookie);
    }
}