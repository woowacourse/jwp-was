package utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

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
        String title = RequestUtils.extractTitleOfModel("/user/create");
        assertThat(title).isEqualTo("USER");
    }

    @Test
    void extractExtension() {
        String extension = RequestUtils.extractExtension("/css/styles.css");
        assertThat(extension).isEqualTo("css");
    }

    @Test
    void extractParameter() {
        Map<String, String> parameter = RequestUtils.extractParameter(
            "userId=javajigi&password=password&name=javajigi&email=javajigi@slipp.net");
        assertAll(
            () -> assertThat(parameter.get("userId")).isEqualTo("javajigi"),
            () -> assertThat(parameter.get("password")).isEqualTo("password"),
            () -> assertThat(parameter.get("name")).isEqualTo("javajigi"),
            () -> assertThat(parameter.get("email")).isEqualTo("javajigi@slipp.net")
        );
    }
}
