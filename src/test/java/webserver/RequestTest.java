package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class RequestTest {

    @DisplayName("Request를 생성할 때 RequestLine을 잘 만드는 지 테스트")
    @Test
    void create() {
        String httpRequest = "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(httpRequest));
        Request request = new Request(bufferedReader);

        RequestLine actual = request.getRequestLine();
        RequestLine expect = new RequestLine(HttpMethod.GET, "/index.html");

        assertThat(actual).isEqualToComparingFieldByField(expect);
    }

    @DisplayName("Request를 생성 시 RequestLine을 만들 때 잘못된 RequestLine인 경우 Exception 발생")
    @Test
    void create2() {
        String httpRequest = "/index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(httpRequest));

        assertThatThrownBy(() -> new Request(bufferedReader))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("잘못된 RequetLine입니다.");
    }

    @DisplayName("BufferedReader가 null일 때 에러 발생")
    @Test
    void create3() {
        assertThatThrownBy(() -> new Request(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferedReader는 null일 수 없습니다.");
    }
}