package kr.wootecat.dongle.model.http.request;

import static kr.wootecat.dongle.model.http.HttpMethod.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestLineTest {

    @DisplayName("클라이언트로부터 입력받는 method, url, version를 통해 HttpRequestLine을 생성한다.")
    @Test
    void crateRequestLine() {
        HttpRequestLine requestLine = HttpRequestLine.from("GET /index.html HTTP/1.1");
        assertAll(
                () -> assertThat(requestLine.getMethod()).isEqualTo(GET),
                () -> assertThat(requestLine.getPath()).isEqualTo("/index.html"),
                () -> assertThat(requestLine.getVersion()).isEqualTo("HTTP/1.1"),
                () -> assertThat(requestLine.isParamEmpty()).isEqualTo(true)
        );
    }
}