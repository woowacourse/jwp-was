package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CookiesTest {
    private Cookie cookie;

    @BeforeEach
    void setUp() {
        final String text = "JSESSIONID=123; logined=true";
        cookie = new Cookie(text);
    }

    @Test
    void 문자열_입력시_파싱_되서_생성되는지_확인() {
        // then
        assertThat(cookie.get(HttpHeaders.JSESSIONID)).isEqualTo("123");
        assertThat(cookie.get("logined")).isEqualTo("true");
    }

    @Test
    void 쿠키_추가_되는지_확인() {
        // given
        final String key = "key";
        final String value = "value";

        // when
        cookie.put(key, value);

        // then
        assertThat(cookie.get(key)).isEqualTo(value);
    }

    @Test
    void 생성_파라미터_NULL_처리() {
        // when
        final Cookie cookie = new Cookie(null);

        // then
        assertThat(cookie.size()).isEqualTo(0);
    }

    @Test
    void 생성_파라미터_공백_처리() {
        // when
        final Cookie cookie = new Cookie("");

        // then
        assertThat(cookie.size()).isEqualTo(0);
    }
}
