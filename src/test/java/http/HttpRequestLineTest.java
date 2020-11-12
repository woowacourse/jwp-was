package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestLineTest {

    @Test
    @DisplayName("httpRequestLine 파싱 테스트")
    void from() {
        HttpRequestLine httpRequestLine = HttpRequestLine.from("GET /someUrl.url HTTP/1.1");

        assertAll(
            () -> assertThat(httpRequestLine.isMethod(HttpMethod.GET)).isTrue(),
            () -> assertThat(httpRequestLine.getUrl()).isEqualTo("/someUrl.url"),
            () -> assertThat(httpRequestLine.getVersion()).isEqualTo("HTTP/1.1")
        );
    }

}
