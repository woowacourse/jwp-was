package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CookiesTest {
    private Cookies cookies;

    @BeforeEach
    void setUp() {
        final String text = "JSESSIONID=123; logined=true";
        cookies = new Cookies(text);
    }

    @Test
    void 문자열_입력시_파싱_되서_생성되는지_확인() {
        // then
        assertThat(cookies.get(Cookies.JSESSIONID).getValue()).isEqualTo("123");
        assertThat(cookies.get("logined").getValue()).isEqualTo("true");
    }

    @Test
    void 쿠키_추가_되는지_확인() {
        // given
        final String key = "key";
        final String value = "value";
        final Cookie cookie = new Cookie(key, value);

        // when
        cookies.add(cookie);

        // then
        assertThat(cookies.get(key)).isEqualTo(cookie);
    }

    @Test
    void 생성_파라미터_NULL_처리() {
        // when
        final Cookies cookies = new Cookies(null);

        // then
        assertThat(cookies.size()).isEqualTo(0);
    }

    @Test
    void 생성_파라미터_공백_처리() {
        // when
        final Cookies cookies = new Cookies("");

        // then
        assertThat(cookies.size()).isEqualTo(0);
    }
}
