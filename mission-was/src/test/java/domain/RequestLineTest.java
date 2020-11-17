package domain;

import static org.assertj.core.api.Assertions.*;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domain.request.RequestLine;
import servlet.StaticFileType;

public class RequestLineTest {

    private static final String GET_HEADER = "GET /user/create?userId=javajigi&password=password&name=pobi&email=javajigi%40slipp.net HTTP/1.1";
    private static final String POST_HEADER = "POST /user/create HTTP/1.1";
    private static final String TEMPLATE_HEADER = "GET /index.html HTTP/1.1";

    @DisplayName("Request Line이 스태틱파일을 요청하는지 확인한다.")
    @Test
    void isStaticFile() throws UnsupportedEncodingException {
        RequestLine requestLine = new RequestLine(TEMPLATE_HEADER);
        assertThat(requestLine.hasPathOfStaticFile()).isTrue();
    }

    @DisplayName("Request Line의 경로가 특정 Path와 동일한지 확인한다.")
    @Test
    void hasEqualPathWith() throws UnsupportedEncodingException {
        RequestLine requestLine = new RequestLine(GET_HEADER);
        assertThat(requestLine.hasEqualPathWith("/user/create")).isTrue();
    }

    @DisplayName("Request Line이 요청한 파일의 확장자를 확인한다.")
    @Test
    void findExtension() throws UnsupportedEncodingException {
        StaticFileType expected = StaticFileType.HTML;
        RequestLine requestLine = new RequestLine(TEMPLATE_HEADER);
        StaticFileType actual = requestLine.findExtension();
        assertThat(actual).isEqualTo(expected);
    }

}
