package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookieTest {
    @Test
    @DisplayName("String으로 들어온 key=value 형태의 문자열을 파싱하여 map으로 저장")
    void create() {
        HttpCookie cookie = HttpCookie.of("key1=value1;key2=value2");

        assertThat(cookie.get("key1")).isEqualTo("value1");
        assertThat(cookie.get("key2")).isEqualTo("value2");
    }
}