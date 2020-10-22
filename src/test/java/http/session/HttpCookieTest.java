package http.session;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpCookieTest {

    @DisplayName("쿠키 추가 및 조회 정장 동작")
    @Test
    void addCookie() {
        HttpCookie httpCookie = new HttpCookie();
        httpCookie.addCookie("KEY", "VALUE");

        assertThat(httpCookie.getCookie("KEY")).isEqualTo("VALUE");
    }

    @DisplayName("기본 생성자 빈 쿠키 확인")
    @Test
    void isEmpty() {
        HttpCookie httpCookie = new HttpCookie();
        assertThat(httpCookie.isEmpty()).isTrue();
    }

    @DisplayName("외부에서 쿠키값 변경 불가")
    @Test
    void unmodifiableCollection() {
        HttpCookie httpCookie = new HttpCookie();
        Map<String, String> cookies = httpCookie.getCookies();

        assertThatThrownBy(() -> cookies.put("KEY", "VALUE"))
            .isInstanceOf(UnsupportedOperationException.class);

    }
}
