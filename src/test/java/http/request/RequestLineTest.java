package http.request;

import http.exception.InvalidHeaderException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestLineTest {

    @Test
    void 생성_성공_테스트() {
        String line = "GET /index.html HTTP/1.1";
        RequestLine requestLine = new RequestLine(line);
        assertThat(requestLine.getPath()).isEqualTo("/index.html");
    }

    @Test
    void 생성_실패_테스트() {
        String line = "GETS /index.html HTTP/1.1";
        assertThrows(InvalidHeaderException.class, () -> new RequestLine(line));
    }

    @Test
    void GET_메소드_테스트() {
        String line = "GET /index.html HTTP/1.1";
        RequestLine requestLine = new RequestLine(line);
        assertThat(requestLine.isGetMethod()).isTrue();
    }

    @Test
    void POST_메소드_테스트() {
        String line = "POST /index.html HTTP/1.1";
        RequestLine requestLine = new RequestLine(line);
        assertThat(requestLine.isPostMethod()).isTrue();
    }

    @Test
    void queryString_테스트() {
        String line = "GET /user/create?userId=seon&password=password&name=sos&email=sos@sos.sos HTTP/1.1";
        String queryString = "userId=seon&password=password&name=sos&email=sos@sos.sos";
        RequestLine requestLine = new RequestLine(line);
        assertThat(requestLine.getQueryString()).isEqualTo(queryString);
    }
}