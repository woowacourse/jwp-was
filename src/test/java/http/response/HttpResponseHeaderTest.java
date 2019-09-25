package http.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseHeaderTest {
    @Test
    void toString_테스트() {
        HttpResponseHeader httpResponseHeader = new HttpResponseHeader();
        httpResponseHeader.addHeader("Content-Length", "1150");
        httpResponseHeader.addHeader("Content-Type", "text/html;charset=utf-8");

        assertThat(httpResponseHeader.toString()).isEqualTo("Content-Length: 1150\r\n" +
                "Content-Type: text/html;charset=utf-8\r\n" +
                "\r\n");
    }
}