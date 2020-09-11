package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestFactoryTest {
    @DisplayName("Request를 만드는 메서드 테스트")
    @Test
    void toRequest() throws IOException {
        String input = "GET /index.html HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*\n"
                + "";
        BufferedReader br = new BufferedReader(new StringReader(input));

        Map<String, String> map = new HashMap<>();
        map.put("Host", "localhost:8080");
        map.put("Connection", "keep-alive");
        map.put("Accept", "*/*");
        RequestHeader expectHeader = new RequestHeader(map);
        RequestUri expectUri = new RequestUri("GET /index.html HTTP/1.1");

        Request actualRequest = RequestFactory.getRequest(br);

        assertAll(
                () -> assertThat(actualRequest.getRequestHeader()).isEqualToComparingFieldByField(expectHeader),
                () -> assertThat(actualRequest.getRequestUri()).isEqualTo(expectUri)
        );
    }
}
