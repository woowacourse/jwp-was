package utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PathUtilsTest {

    @Test
    public void parsePath() {
        String line = "GET / HTTP/1.1";
        String requestUrl = PathUtils.parsePath(line);
        assertThat(requestUrl).isEqualTo("/");
    }
}