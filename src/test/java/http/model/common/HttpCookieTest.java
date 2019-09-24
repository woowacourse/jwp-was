package http.model.common;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HttpCookieTest {

    @Test
    void 기본생성자_null_safe() {
        assertDoesNotThrow(() -> new HttpCookie().getCookies());
    }

    @Test
    void add호출시_내부맵_변경_테스트() {
        HttpCookie httpCookie = new HttpCookie();
        httpCookie.addCookie("key", "value");

        assertThat(httpCookie.getCookie("key")).isEqualTo("value");
    }

    @Test
    void 외부에서_내부맵_변경불가_테스트() {
        HttpCookie httpCookie = new HttpCookie();
        Map<String, String> map = httpCookie.getCookies();

        assertThatThrownBy(() -> map.put("some", "value")).isInstanceOf(UnsupportedOperationException.class);
    }
}