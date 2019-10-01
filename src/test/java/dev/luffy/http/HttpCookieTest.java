package dev.luffy.http;

import dev.luffy.http.excption.NotFoundCookieException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HttpCookieTest {

    private HttpCookie httpCookie;

    @BeforeEach
    void setUp() {
        httpCookie = new HttpCookie();
    }

    @DisplayName("존재하지 않는 Cookie 에 대한 get 메서드는 에러를 던진다.")
    @Test
    void getCookieValueWhenNotExist() {
        assertThrows(NotFoundCookieException.class, () -> httpCookie.get("not_exist"));
    }

    @DisplayName("Cookie 가 존재하면 해당 값을 성공적으로 가져온다.")
    @Test
    void getCookieValueSuccess() {
        httpCookie.put("cookie", "test");
        assertEquals(httpCookie.get("cookie"), "test");
    }

    @DisplayName("여러 개의 Cookie 가 담긴 Map 을 이용해 addCookies 메서드를 성공적으로 실행한다.")
    @Test
    void addCookiesUsingMap() {
        Map<String, String> cookie = new HashMap<>();
        cookie.put("cookie", "test");
        cookie.put("jwp", "was");
        cookie.put("delicious", "chocolate cookie");
        httpCookie.addCookies(cookie);
        assertEquals(httpCookie.get("cookie"), "test");
        assertEquals(httpCookie.get("jwp"), "was");
        assertEquals(httpCookie.get("delicious"), "chocolate cookie");
    }
}