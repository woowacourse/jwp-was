package http.common;

import http.common.Cookie;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {

    @Test
    void 쿠키_생성_테스트() {
        Cookie cookie = new Cookie("logined", "true");
        assertThat(cookie.getFieldString()).isEqualTo("logined=true");
    }

    @Test
    void 쿠키_옵션_추가_테스트() {
        Cookie cookie = new Cookie("logined", "true");
        cookie.addOption("Path","/");
        assertThat(cookie.getFieldString()).isEqualTo("logined=true; Path=/");
    }

    @Test
    void Request_Cookie_생성_테스트() {
        Cookie cookie = new Cookie("logined=true");
        assertThat(cookie.getFieldString()).isEqualTo("logined=true");
    }
}