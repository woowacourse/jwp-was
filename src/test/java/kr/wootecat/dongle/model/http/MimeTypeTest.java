package kr.wootecat.dongle.model.http;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.wootecat.dongle.model.http.exception.UnsupportedMimeTypeException;

class MimeTypeTest {

    @DisplayName("서버에서 지원하는 MIME 타입을 입력 받는 경우 해당하는 enum 값 반환")
    @Test
    void supported_mime_type_then_return_mime_enum() {
        MimeType htmlType = MimeType.of("/index.html");
        assertThat(htmlType).isEqualTo(MimeType.HTML_UTF_8);
    }

    @DisplayName("서버에서 지원하지 않는 MIME 타입을 입력 받는 경우 UnsupportedMimeTypeException 발생")
    @Test
    void unsupportedMimeType_then_throw_exception() {
        assertThatThrownBy(() -> MimeType.of("core/http/mimetype.java"))
                .isInstanceOf(UnsupportedMimeTypeException.class);
    }
}