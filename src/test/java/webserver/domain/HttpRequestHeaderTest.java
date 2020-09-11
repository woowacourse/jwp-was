package webserver.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestHeaderTest {

    private static final String POST_HEADER = "POST /user/create HTTP/1.1\n"
        + "Host: localhost:8080\n"
        + "Connection: keep-alive\n"
        + "Content-Length: 59\n"
        + "Content-Type: application/x-www-form-urlencoded\n"
        + "Accept: */*";

    @DisplayName("헤더를 입력받아서 RequestHeader 클래스에 담기는지 확인한다.")
    @Test
    void getRequestMethod() throws UnsupportedEncodingException {
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(POST_HEADER);
        assertAll(
            () -> assertThat(httpRequestHeader.getRequestMethod()).isEqualTo(RequestMethod.POST),
            () -> assertThat(httpRequestHeader.getPath()).isEqualTo("/user/create"),
            () -> assertThat(httpRequestHeader.findContentLength()).isEqualTo(59)
        );
    }
}
