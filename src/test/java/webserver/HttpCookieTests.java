package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.controller.HttpCookie;

import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static webserver.controller.HttpCookie.FALSE;
import static webserver.controller.HttpCookie.TRUE;

public class HttpCookieTests {
    private HttpCookie httpCookie;

    @BeforeEach
    void setUp() {
        httpCookie = new HttpCookie();
    }

    @Test
    void login_cookie() {
        String path = "/";
        httpCookie.loginCookie(true, path);
        Map<String, String> frontField = httpCookie.getFrontField();
        Map<String, String> pairOption = httpCookie.getPairOption();
        assertThat(pairOption.get("Path")).isEqualTo(path);
        assertThat(frontField.get("logined")).isEqualTo(TRUE);
    }

    @Test
    void not_login_cookie() {
        String path = "/";
        httpCookie.loginCookie(false, path);
        Map<String, String> frontField = httpCookie.getFrontField();
        Map<String, String> pairOption = httpCookie.getPairOption();
        assertThat(pairOption.get("Path")).isEqualTo(path);
        assertThat(frontField.get("logined")).isEqualTo(FALSE);
    }
}
