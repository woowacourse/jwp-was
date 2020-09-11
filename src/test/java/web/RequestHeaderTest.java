package web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHeaderTest {

    @DisplayName("요청 받은 정보 리스트에서 Headers 를 생성한다.")
    @Test
    void parseHeader() {
        final ArrayList<String> requestHeaders = new ArrayList<>();
        requestHeaders.add("Host: localhost:8080");
        requestHeaders.add("Connection: keep-alive");
        requestHeaders.add("Content-Length: 59");

        final RequestHeader header = new RequestHeader(requestHeaders);

        assertThat(header.getHeaders().keySet()).containsOnly("Host", "Connection", "Content-Length");
        assertThat(header.getHeaders().values()).containsOnly("localhost:8080", "keep-alive", "59");
    }

    @DisplayName("RequestHeader로부터 ContentLength를 받아올 수 있다.")
    @Test
    void getContentLength() {
        final ArrayList<String> requestHeaders = new ArrayList<>();
        requestHeaders.add("Host: localhost:8080");
        requestHeaders.add("Connection: keep-alive");
        requestHeaders.add("Content-Length: 59");

        final RequestHeader header = new RequestHeader(requestHeaders);

        assertThat(header.getContentLength()).isEqualTo(59);
    }
}