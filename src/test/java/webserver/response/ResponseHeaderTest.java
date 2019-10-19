package webserver.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.support.ConStants.CONTENT_TYPE_CSS;
import static webserver.support.ConStants.HEADER_FIELD_CONTENT_TYPE;

public class ResponseHeaderTest {
    @DisplayName("Response 헤더 필드 추가")
    @Test
    void addAttribute() {
        ResponseHeader responseHeader = new ResponseHeader();
        assertThat(responseHeader.addAttribute(HEADER_FIELD_CONTENT_TYPE, CONTENT_TYPE_CSS)).isTrue();
    }

    @DisplayName("이미 존재하는 Response 헤더필드 추가")
    @Test
    void addDuplicatedAttribute() {
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.addAttribute(HEADER_FIELD_CONTENT_TYPE, CONTENT_TYPE_CSS);
        assertThat(responseHeader.addAttribute(HEADER_FIELD_CONTENT_TYPE, "text/javascript")).isFalse();
    }

    @DisplayName("Response 헤더필드 ")
    @Test
    void response() {
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.addAttribute(HEADER_FIELD_CONTENT_TYPE, CONTENT_TYPE_CSS);
        assertThat(responseHeader.response()).isEqualTo(HEADER_FIELD_CONTENT_TYPE + ": " + CONTENT_TYPE_CSS + "\r\n");
    }
}
