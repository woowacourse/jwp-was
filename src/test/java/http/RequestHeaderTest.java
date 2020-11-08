package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static http.HttpRequestTest.NEW_LINE;
import static org.assertj.core.api.Assertions.assertThat;

class RequestHeaderTest {

    @DisplayName("요청 받은 정보 리스트에서 Headers 를 생성한다.")
    @Test
    void parseHeader() throws IOException {
        String httpRequestInput = "Content-Type: text/html;charset=UTF-8" + NEW_LINE
                + "Content-Length: 93" + NEW_LINE
                + "Accept-Language: en-US,en;q=0.9" + NEW_LINE
                + NEW_LINE;
        Reader inputString = new StringReader(httpRequestInput);
        BufferedReader br = new BufferedReader(inputString);

        RequestHeader header = new RequestHeader(br);

        assertThat(header.getHeaders().keySet()).containsOnly("Content-Length", "Content-Type", "Accept-Language");
        assertThat(header.getHeaders().values()).containsOnly("text/html;charset=UTF-8", "93", "en-US,en;q=0.9");
    }

    @DisplayName("RequestHeader로부터 ContentLength를 받아올 수 있다.")
    @Test
    void getContentLength() throws IOException {
        String httpRequestInput = "Content-Type: text/html;charset=UTF-8" + NEW_LINE
                + "Content-Length: 93" + NEW_LINE
                + "Accept-Language: en-US,en;q=0.9" + NEW_LINE
                + NEW_LINE;
        Reader inputString = new StringReader(httpRequestInput);
        BufferedReader br = new BufferedReader(inputString);

        RequestHeader header = new RequestHeader(br);

        assertThat(header.getContentLength()).isEqualTo(93);
    }

    @DisplayName("Header에 ContentLength 정보가 없으면 길이 0을 반환한다.")
    @Test
    void getZeroContentLength() throws IOException {
        String httpRequestInput = "Content-Type: text/html;charset=UTF-8" + NEW_LINE
                + "Accept-Language: en-US,en;q=0.9" + NEW_LINE
                + NEW_LINE;
        Reader inputString = new StringReader(httpRequestInput);
        BufferedReader br = new BufferedReader(inputString);

        RequestHeader header = new RequestHeader(br);

        assertThat(header.getContentLength()).isEqualTo(0);
    }
}