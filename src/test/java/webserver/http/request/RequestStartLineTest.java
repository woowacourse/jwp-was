package webserver.http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestStartLineTest {
    @DisplayName("생성 테스트")
    @Test
    void from() {
        String url = "/index.html";
        RequestStartLine requestStartLine = RequestStartLine.from("GET " + url + " HTTP/1.1");

        assertThat(requestStartLine.getHttpMethod()).isEqualTo(RequestMethod.GET);
        assertThat(requestStartLine.getUrl()).isEqualTo(url);
    }
}