package webserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MediaTypeTest {

    @Test
    @DisplayName("파일 확장자명에 대한 객체를 반환하는지 확인")
    void of1() {
        String extension = "htm";
        assertThat(MediaType.of(extension)).isEqualTo(MediaType.TEXT_HTML);
    }

    @Test
    @DisplayName("임의의 파일 확장자명에 대해 binary 객체를 반환하는지 확인")
    void of2() {
        String extension = "kunggom";
        assertThat(MediaType.of(extension)).isEqualTo(MediaType.APPLICATION_BINARY);
    }

    @Test
    @DisplayName("미디어타입을 잘 반환하는지 확인")
    void getMediaType() {
        String extension = "htm";
        assertThat(MediaType.of(extension).is()).isEqualTo("text/html");
    }
}