package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RequestUtilsTest {

    @DisplayName("Request Line에서 Path 추출")
    @Test
    void extractPath() {
        String url = RequestUtils.extractFilePath("GET /index.html HTTP/1.1");

        assertThat(url).isEqualTo("/index.html");
    }
}