package http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpCookieTest {

    @Test
    public void cookieTest() {
        HttpCookie httpCookie = new HttpCookie("logined=true; Path=/");
        assertThat(httpCookie.getValue("logined")).isEqualTo("true");
        assertThat(httpCookie.getValue("Path")).isEqualTo("/");
    }
}