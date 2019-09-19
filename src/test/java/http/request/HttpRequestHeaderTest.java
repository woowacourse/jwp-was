package http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestHeaderTest {

    @Test
    void ContentLength_있음() {
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader("Content-Length: 6902");
        assertThat(httpRequestHeader.getContentLength()).isEqualTo(6902);
    }

    @Test
    void ContentLength_없음() {
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader("Content-Type: text/html;charset=utf-8");
        assertThat(httpRequestHeader.getContentLength()).isEqualTo(0);
    }
}