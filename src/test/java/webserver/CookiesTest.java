package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CookiesTest {

    @DisplayName("Cookies 생성")
    @Test
    void constructor() {
        List<Cookie> cookies = Collections.singletonList(new Cookie("name", "bingbong"));

        assertThat(new Cookies(cookies)).isNotNull();
    }

    @DisplayName("Cookie 이름으로 조회")
    @Test
    void getCookie() {
        List<Cookie> cookiesData = Collections.singletonList(new Cookie("name", "bingbong"));
        Cookies cookies = new Cookies(cookiesData);

        assertThat(cookies.getCookie("name").isPresent()).isTrue();
    }

    @DisplayName("Cookie 전체 조회")
    @Test
    void getCookies() {
        List<Cookie> cookiesData = Collections.singletonList(new Cookie("name", "bingbong"));
        Cookies cookies = new Cookies(cookiesData);

        assertThat(cookies.getCookies()).hasSize(1);
    }
}
