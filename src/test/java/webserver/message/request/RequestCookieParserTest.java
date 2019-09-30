package webserver.message.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.message.HttpCookie;
import webserver.support.RequestHelper;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class RequestCookieParserTest extends RequestHelper {
    @Test
    @DisplayName("쿠키 파싱을 제대로 하는지 테스트")
    void getCookie() {

        Set<HttpCookie> cookies = new HashSet<>(RequestCookieParser.parse("sessionId=115da514-e829344079; logined=false"));

        Set<HttpCookie> expect = new HashSet<>();
        expect.add(new HttpCookie.Builder("sessionId", "115da514-e829344079").build());
        expect.add(new HttpCookie.Builder("logined", "false").build());

        assertThat(cookies).isEqualTo(expect);
    }

}