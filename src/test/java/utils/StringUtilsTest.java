package utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StringUtilsTest {

    @Test
    public void extractPath() {
        String path = StringUtils.extractPath("GET /index.html HTTP/1.1");

        assertThat(path).isEqualTo("/index.html");
    }

}