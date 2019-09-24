package http.request;

import http.common.HttpVersion;
import http.common.URL;
import http.request.exception.InvalidRequestException;
import http.request.exception.InvalidRequestMethodException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestLineTest {

    @Test
    void 정상_생성() {
        RequestLine requestLine = new RequestLine("GET / HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(RequestMethod.GET);
        assertThat(requestLine.getUrl()).isEqualTo(URL.of("/"));
        assertThat(requestLine.getVersion()).isEqualTo(HttpVersion.HTTP_1_1);
    }

    @Test
    void 잘못된_메서드() {
        assertThrows(InvalidRequestMethodException.class, () -> new RequestLine("WRONG / HTTP/1.1"));
    }

    //Todo : 테스트 케이스 추가하기 (parameterized)
    @Test
    void 잘못된_request_line_패턴() {
        assertThrows(InvalidRequestException.class, () -> new RequestLine("GET /"));
    }

}