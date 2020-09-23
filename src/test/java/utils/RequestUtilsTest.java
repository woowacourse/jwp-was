package utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RequestUtilsTest {
    @Test
    void isPost() {
        String get = "GET /index.html HTTP/1.1";
        String post = "POST /index.html HTTP/1.1";
        assertAll(
            () -> assertThat(RequestUtils.extractMethod(get)).isEqualTo("GET"),
            () -> assertThat(RequestUtils.extractMethod(post)).isEqualTo("POST")
        );
    }

    @Test
    void extractPath() {
        String url = RequestUtils.extractPath("GET /index.html HTTP/1.1");
        assertThat(url).isEqualTo("/index.html");
    }

    @Test
    void extractTitleOfModel() {
        String title = RequestUtils.extractTitleOfModel("GET /user/create HTTP/1.1");
        assertThat(title).isEqualTo("USER");
    }

    @Test
    void extractExtension() {
        String extension = RequestUtils.extractExtension("/css/styles.css");
        assertThat(extension).isEqualTo("css");
    }
}
