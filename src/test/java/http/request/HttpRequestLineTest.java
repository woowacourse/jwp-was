package http.request;

import http.exception.RequestLineException;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpRequestLineTest {
    @Test
    void HttpRequestLine_생성() {
        String requestLine = "GET /index HTTP/1.1";

        assertThatCode(() -> HttpRequestLine.of(requestLine))
                .doesNotThrowAnyException();
    }

    @Test
    void request_line_parsing_결과가_3개가_아니면_예외_발생() throws UnsupportedEncodingException {
        String requestLine = "GET /index\n";

        assertThatThrownBy(() -> HttpRequestLine.of(requestLine))
                .isInstanceOf(RequestLineException.class);
    }
}