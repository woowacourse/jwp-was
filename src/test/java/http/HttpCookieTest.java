package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookieTest {

    public static final String PATH = "/hello";

    @Test
    void 쿠키_생성() {
        String key = "SessionId";
        String value = "keke";
        HttpCookie httpCookie = new HttpCookie(key, value);

        httpCookie.setPath("/hello");

        assertThat(httpCookie.getResponse()).isEqualTo(key + "=" + value + "; " + "Path=" + PATH + "; ");

    }


}