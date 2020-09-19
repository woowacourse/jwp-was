package webserver.http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestLineTest {
    @DisplayName("생성 테스트")
    @Test
    void from() {
        String url = "/index.html";
        RequestLine requestLine = RequestLine.from("GET " + url + " HTTP/1.1");

        assertThat(requestLine.getHttpMethod()).isEqualTo(RequestMethod.GET);
        assertThat(requestLine.getUrl()).isEqualTo(url);
    }
}