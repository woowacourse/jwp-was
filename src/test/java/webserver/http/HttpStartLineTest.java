package webserver.http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpStartLineTest {
    @DisplayName("생성 테스트")
    @Test
    void from() {
        String url = "/index.html";
        HttpStartLine httpStartLine = HttpStartLine.from("GET " + url + " HTTP/1.1");

        assertThat(httpStartLine.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpStartLine.getUrl()).isEqualTo(url);
    }
}