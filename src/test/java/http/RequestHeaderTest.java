package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;

class RequestHeaderTest {
    @DisplayName("요청 url을 가져오는 기능 테스트")
    @Test
    void findRequestUrl() {
        String input = "GET /index.html HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*";
        BufferedReader br = new BufferedReader(new StringReader(input));

//        Request request = RequestFactory.getRequest(br);
//        String requestUrl = requestHeader.findRequestUrl();

//        assertThat(requestUrl).isEqualTo("/index.html");
    }
}
