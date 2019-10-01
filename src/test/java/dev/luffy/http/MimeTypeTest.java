package dev.luffy.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MimeTypeTest {

    @DisplayName("성공적으로 MimeType 을 생성한다.")
    @Test
    void constructMimeTypeSuccessfully() {
        assertEquals(MimeType.of(".html"), MimeType.HTML);
    }

    @DisplayName("지원하지 않는 확장자는 모두 MimeType.OTHER 이다.")
    @Test
    void constructMimeTypeOther() {
        assertEquals(MimeType.of(".avi"), MimeType.OTHER);
        assertEquals(MimeType.of(".mp4"), MimeType.OTHER);
        assertEquals(MimeType.of(".luffy"), MimeType.OTHER);
    }

    @DisplayName("isHtml 메서드가 정상적으로 동작한다.")
    @Test
    void isHtml() {
        assertTrue(MimeType.HTML.isHtml());
        assertFalse(MimeType.CSS.isHtml());
        assertFalse(MimeType.SVG.isHtml());
    }
}