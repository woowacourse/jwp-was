package utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PathUtilsTest {

    @Test
    void extractPath() {
        String requestLine = "GET /index.html HTTP/1.1";
        String path = PathUtils.extractPath(requestLine);

        assertThat(path).isEqualTo("./templates/index.html");
    }
}