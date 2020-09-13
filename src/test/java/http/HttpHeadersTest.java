package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpHeadersTest {

    @DisplayName("올바른 헤더 정보로 HttpHeader 객체를 생성한다.")
    @Test
    void createHttpHeader() {
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", "128");
        headers.put("Accept-Language", "en-US,en;q=0.9");
        headers.put("Cookie", "JSESSIONID=D03222408A13F2797D6DFECB7CFC74EE");
        HttpHeaders httpHeaders = new HttpHeaders(headers);

        assertAll(
            () -> assertThat("text/html;charset=utf-8").isEqualTo(httpHeaders.getValue("Content-Type")),
            () -> assertThat("128").isEqualTo(httpHeaders.getValue("Content-Length")),
            () -> assertThat("en-US,en;q=0.9").isEqualTo(httpHeaders.getValue("Accept-Language")),
            () -> assertThat("JSESSIONID=D03222408A13F2797D6DFECB7CFC74EE").isEqualTo(httpHeaders.getValue("Cookie"))
        );
    }
}