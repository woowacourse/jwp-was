package webserver;

import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CookiesTest {

    @DisplayName("Cookies 생성")
    @Test
    void constructor() {
        List<Cookie> cookies = Collections.singletonList(new Cookie("name", "bingbong"));

        Assertions.assertThat(new Cookies(cookies)).isNotNull();
    }

    @DisplayName("Cookie 이름으로 조회")
    @Test
    void getCookie() {
        List<Cookie> cookiesData = Collections.singletonList(new Cookie("name", "bingbong"));
        Cookies cookies = new Cookies(cookiesData);

        Assertions.assertThat(cookies.getCookie("name").isPresent()).isTrue();
    }

    @DisplayName("Cookie 전체 조회")
    @Test
    void getCookies() {
        List<Cookie> cookiesData = Collections.singletonList(new Cookie("name", "bingbong"));
        Cookies cookies = new Cookies(cookiesData);

        Assertions.assertThat(cookies.getCookies()).hasSize(1);
    }
}
