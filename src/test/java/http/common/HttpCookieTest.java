package http.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookieTest {
    private HttpCookie httpCookie;

    @BeforeEach
    void setUp() {
        httpCookie = HttpCookie.of("logined=true; id=aiden");
    }

    @Test
    @DisplayName("Cookie 생성 테스트")
    public void create() {
        assertThat(httpCookie.getCookie("logined")).isEqualTo("true");
        assertThat(httpCookie.getCookie("id")).isEqualTo("aiden");
    }

    @Test
    @DisplayName("Cookie put() 테스트")
    void putTest() {
        httpCookie.put("id", "aiden");
        assertThat(httpCookie.getCookie("id")).isEqualTo("aiden");
    }

    @Test
    @DisplayName("Cookie keySet() 테스트")
    void keySetTest() {
        Set<String> keys = new HashSet<>(Arrays.asList("logined", "id"));
        assertThat(keys).isEqualTo(httpCookie.keySet());
    }
}