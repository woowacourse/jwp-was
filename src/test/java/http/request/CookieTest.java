package http.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {
    private String rawCookie = "name1=rebecca; name2=hiro;";
    private Cookie cookie;

    @BeforeEach
    void setUp() {
        cookie = new Cookie(rawCookie);
    }

    @Test
    @DisplayName("쿠키가 잘 들어 있는지 확인하는 테스트")
    void getCookieTest() {
        assertThat(cookie.getCookie("name1")).isEqualTo("rebecca");
        assertThat(cookie.getCookie("name2")).isEqualTo("hiro");
    }

    @Test
    @DisplayName("해당 키값의 쿠키가 있는지 확인하는 테스트")
    void hasCookieTest() {
        assertThat(cookie.hasCookie("name1")).isTrue();
        assertThat(cookie.hasCookie("name2")).isTrue();
    }

    @Test
    @DisplayName("쿠키를 추가하는 테스트")
    void setCookieTest() {
        cookie.setCookie("name3", "pobi");
        assertThat(cookie.getCookie("name3")).isEqualTo("pobi");
    }

    @Test
    @DisplayName("모든 쿠키의 Set을 가져오는 테스트")
    void getAllCookieTest() {
        assertThat(cookie.getAllCookie().size()).isEqualTo(2);
    }

}