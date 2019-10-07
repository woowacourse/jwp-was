package http.request;

import http.common.HttpCookie;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookiesTest {
    private static final String JSESSIONID = "JSESSIONID";
    private static final String LOGINED = "logined";

    @Test
    void 생성_및_쿠키_조회_테스트() {
        Map<String, HttpCookie> httpCookieMap = new HashMap<>();
        HttpCookie jSessionIdCookie = HttpCookie.builder(JSESSIONID, UUID.randomUUID().toString()).build();
        httpCookieMap.put(JSESSIONID, jSessionIdCookie);

        HttpCookie loginedCookie = HttpCookie.builder("logined", "true").build();
        httpCookieMap.put(LOGINED, loginedCookie);

        HttpCookies httpCookies = new HttpCookies(httpCookieMap);

        assertThat(httpCookies.get(JSESSIONID)).isEqualTo(jSessionIdCookie);
        assertThat(httpCookies.get(LOGINED)).isEqualTo(loginedCookie);
    }
}