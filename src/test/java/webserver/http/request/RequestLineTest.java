package webserver.http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestLineTest {
    @DisplayName("생성 테스트")
    @Test
    void from() {
        String url = "/index.html";
        RequestLine requestLine = RequestLine.from("GET " + url + " HTTP/1.1");

        assertThat(requestLine.getHttpMethod()).isEqualTo(RequestMethod.GET);
        assertThat(requestLine.getUrl()).isEqualTo(url);
    }

    @DisplayName("메소드가 body를 지원하는 지 확인한다.")
    @Test
    void allowBody() {
        String url = "/index.html";
        RequestLine getRequestLine = RequestLine.from("GET " + url + " HTTP/1.1");
        RequestLine postRequestLine = RequestLine.from("POST " + url + " HTTP/1.1");

        assertAll(
            () -> assertThat(getRequestLine.allowBody()).isFalse(),
            () -> assertThat(postRequestLine.allowBody()).isTrue()
        );
    }

    @DisplayName("지원하지 않는 메소드가 입력될 경우 false를 반환한다.")
    @Test
    void allowBodyWithNull() {
        String url = "/index.html";
        RequestLine requestLine = RequestLine.from("HELLO " + url + " HTTP/1.1");

        assertThat(requestLine.allowBody()).isFalse();
    }
}