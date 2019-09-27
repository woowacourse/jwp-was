package webserver.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseHeaderTest {
    @DisplayName("Response 헤더 필드 추가")
    @Test
    void addAttribute() {
        ResponseHeader responseHeader = new ResponseHeader();
        assertThat(responseHeader.addAttribute("Content-Type", "text/css")).isTrue();
    }

    @DisplayName("이미 존재하는 Response 헤더필드 추가")
    @Test
    void addDuplicatedAttribute() {
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.addAttribute("Content-Type", "text/css");
        assertThat(responseHeader.addAttribute("Content-Type", "text/javascript")).isFalse();
    }

    @DisplayName("Response 헤더필드 ")
    @Test
    void response() {
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.addAttribute("Content-Type", "text/css");
        assertThat(responseHeader.response()).isEqualTo("Content-Type: text/css\r\n");
    }
}
