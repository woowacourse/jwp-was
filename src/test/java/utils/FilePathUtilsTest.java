package utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FilePathUtilsTest {
    @Test
    void getExtension() {
        String path = "/index.html";
        assertThat(FilePathUtils.getExtension(path)).isEqualTo("html");
    }
}