package http.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookiesTest {

    private Cookies cookies;

    @BeforeEach
    void setUp() {
        cookies = new Cookies("firstName=firstValue");
    }

    @Test
    void getCookie() {
        assertThat(cookies.getCookie("firstName")).isEqualTo("firstValue");
    }

    @Test
    void addCookie() {
        cookies.addCookie(new Cookie("secondName", "secondValue"));
        assertThat(cookies.getCookie("secondName")).isEqualTo("secondValue");
    }

    @Test
    void hasCookie() {
        assertThat(cookies.hasCookie()).isTrue();

        Cookies cookies2 = new Cookies();
        assertThat(cookies2.hasCookie()).isFalse();
    }
}