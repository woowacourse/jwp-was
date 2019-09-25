package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MediaTypeTest {

    @Test
    @DisplayName("전체 경로 만들기")
    void getFullPath() {
        assertThat(MediaType.getFullPath("/index.html")).isEqualTo("./templates/index.html");
    }


    @Test
    @DisplayName("지원하는 확장자")
    void getContentType() {
        assertThat(MediaType.getContentType("/test.css")).isEqualTo("text/css");
    }

    @Test
    @DisplayName("지원하지 않는 확장자")
    void tryNotSupportContentType() {
        assertThrows(IllegalArgumentException.class, () -> MediaType.getContentType("/test.abc"));
    }


    @Test
    @DisplayName("확장자를 가지고 있는 경우")
    void isContainExtension() {
        assertTrue(MediaType.isContain("/test.css"));
    }

    @Test
    @DisplayName("확장자가 없는 경우")
    void isNotContainExtension() {
        assertFalse(MediaType.isContain("/test"));
    }
}