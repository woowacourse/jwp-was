package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static http.HttpRequestTest.NEW_LINE;
import static org.assertj.core.api.Assertions.assertThat;

class ResponseHeaderTest {

    @DisplayName("HttpResponse에 전달하기 위한 Header를 문자열로 읽어올 수 있다.")
    @Test
    void readHeaders() {
        String expected = "Accept-Language: en-US,en;q=0.9" + NEW_LINE
                + "Content-Length: 93" + NEW_LINE
                + "Content-Type: text/html;charset=UTF-8" + NEW_LINE;

        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.addHeader("Content-Length", "93");
        responseHeader.addHeader("Accept-Language", "en-US,en;q=0.9");
        responseHeader.addHeader("Content-Type", "text/html;charset=UTF-8");

        String actual = responseHeader.readHeaders();

        assertThat(actual).isEqualTo(expected);
    }
}