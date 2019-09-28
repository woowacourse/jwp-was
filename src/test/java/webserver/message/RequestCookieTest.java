package webserver.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.message.request.RequestHeader;
import webserver.support.RequestHelper;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestCookieTest extends RequestHelper {
    @Test
    @DisplayName("쿠키 파싱을 제대로 하는지 테스트")
    void getCookie() {
        RequestHeader requestHeader = new RequestHeader(ioUtils(requestHeaderWithCookie));
        Map<String, String> map = new HashMap<>();
        map.put("logined", "true");
        map.put("Path", "/");

        assertThat(requestHeader.getRequestCookie().getCookie()).isEqualTo(map);
    }

}