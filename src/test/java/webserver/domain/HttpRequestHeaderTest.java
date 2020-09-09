package webserver.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import utils.StaticFileType;

class HttpRequestHeaderTest {

    private static final String GET_HEADER =
        "GET /user/create?userId=javajigi&password=password&name=pobi&email=javajigi%40slipp.net HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*";

    private static final String POST_HEADER = "POST /user/create HTTP/1.1\n"
        + "Host: localhost:8080\n"
        + "Connection: keep-alive\n"
        + "Content-Length: 59\n"
        + "Content-Type: application/x-www-form-urlencoded\n"
        + "Accept: */*";

    private static final String TEMPLATE_HEADER = "GET /index.html HTTP/1.1\n"
        + "Host: localhost:8080\n"
        + "Connection: keep-alive\n"
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

    @DisplayName("RequestHeader에서 스태틱파일을 요청하는지 확인한다.")
    @Test
    void isStaticFile() throws UnsupportedEncodingException {
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(TEMPLATE_HEADER);
        assertThat(httpRequestHeader.isStaticFile()).isTrue();
    }

    @DisplayName("입력한 Path가 RequestHeader의 Path와 동일한지 확인한다.")
    @Test
    void hasEqualPathWith() throws UnsupportedEncodingException {
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(GET_HEADER);
        assertThat(httpRequestHeader.hasEqualPathWith("/user/create")).isTrue();
    }

    @DisplayName("RequestHeader의 요청파일의 확장자를 확인한다.")
    @Test
    void findExtension() throws UnsupportedEncodingException {
        StaticFileType expected = StaticFileType.HTML;

        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(TEMPLATE_HEADER);
        StaticFileType actual = httpRequestHeader.findExtension();

        assertThat(actual).isEqualTo(expected);
    }
}
