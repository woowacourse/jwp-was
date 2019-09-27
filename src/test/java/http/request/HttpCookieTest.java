package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookieTest {

    @Test
    @DisplayName("쿠키가 두개 이상")
    void getCookie() {
        HttpCookie httpCookie = HttpCookie.of("srftoken=VLcTfULqEDjd5LMaY4fTDxSYaZUHFnEhWDY4uioygZOLVyzHTIerHILgDiiWf0NO; logined=true");
        assertThat(httpCookie.getCookie("logined")).isEqualTo("true");
    }

    @Test
    @DisplayName("쿠키가 하나일 떄")
    void oneCookie() {
        HttpCookie httpCookie = HttpCookie.of("logined=true");
        assertThat(httpCookie.getCookie("logined")).isEqualTo("true");
    }
}