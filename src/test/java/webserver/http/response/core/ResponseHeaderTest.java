package webserver.http.response.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpHeaderField;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseHeaderTest {

    @Test
    @DisplayName("Response Header가 잘 들어가는지 테스트")
    void responseHeaderTest() {
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.addHeaders(HttpHeaderField.CONTENT_TYPE, ResponseContentType.CSS);
        responseHeader.addHeaders(HttpHeaderField.CONTENT_LENGTH, "6902");
        String testHeader = "Content-Type: text/css;charset=utf-8\r\n" +
                "Content-Length: 6902";
        assertThat(responseHeader.getResponseHeaders()).isEqualTo(testHeader);
    }

}