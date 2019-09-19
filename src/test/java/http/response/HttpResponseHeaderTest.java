package http.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseHeaderTest {
    @Test
    void toString_테스트() {
        String headers = "Content-Length: 1150\n" +
                "Content-Type: text/html;charset=utf-8";

        HttpResponseHeader httpResponseHeader = new HttpResponseHeader(headers);
        assertThat(httpResponseHeader.toString()).isEqualTo("Content-Length: 1150\n" +
                "Content-Type: text/html;charset=utf-8\n" +
                "\r\n" +
                "\r\n");
    }
}