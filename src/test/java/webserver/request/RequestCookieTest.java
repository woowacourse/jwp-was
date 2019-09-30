package webserver.request;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestCookieTest {
    @Test
    void getCookie_logined_true(){
        Map<String, String> cookies = new HashMap<>();
        cookies.put("Idea-e98840a1", "365d551c-5d0c-4192-923c-a0070fb208db");
        cookies.put("logined", "true");

        RequestCookie requestCookie = new RequestCookie(cookies);
        assertThat(requestCookie.getCookie("logined")).isEqualTo("true");
        assertThat(requestCookie.getCookie("Idea-e98840a1")).isEqualTo("365d551c-5d0c-4192-923c-a0070fb208db");
    }
}