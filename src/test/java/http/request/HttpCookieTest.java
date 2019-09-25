package http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookieTest {

    @Test
    void getCookie() {
        HttpCookie httpCookie = HttpCookie.of("srftoken=VLcTfULqEDjd5LMaY4fTDxSYaZUHFnEhWDY4uioygZOLVyzHTIerHILgDiiWf0NO; logined=true");
        assertThat(httpCookie.getCookie("logined")).isEqualTo("true");
    }
}