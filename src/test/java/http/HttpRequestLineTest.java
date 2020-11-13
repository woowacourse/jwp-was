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
            () -> assertThat(httpRequestLine.getVersion()).isEqualTo("HTTP/1.1")
        );
    }

    @Test
    @DisplayName("올바르지 않은 request line 입력시 예외처리")
    void fromError() {
        assertThatThrownBy(() -> HttpRequestLine.from("GET"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("request line 의 구성요소(method, url, version)이 모두 존재하지 않습니다.");
    }

}
