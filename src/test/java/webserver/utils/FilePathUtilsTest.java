package webserver.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FilePathUtilsTest {
    @Test
    @DisplayName("path 에서 extension 추출")
    void getExtension() {
        String path = "/index.html";
        assertThat(FilePathUtils.getExtension(path)).isEqualTo("html");
    }

    @Test
    @DisplayName("prefix 확인 후 path 반환")
    void getResourcePath() {
        String pathWithPrefix = "/index.html";
        String pathWithoutPrefix = "index.html";
        assertThat(FilePathUtils.getResourcePath(pathWithPrefix))
                .isEqualTo(FilePathUtils.getResourcePath(pathWithoutPrefix));
    }
}