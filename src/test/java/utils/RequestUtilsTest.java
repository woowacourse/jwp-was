package utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

class RequestUtilsTest {
    @Test
    void extractMethod() {
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

    @Test
    void logined() {
        String cookie = "Idea-887e564c=c5cf03c1-8b9e-4ade-82f6-76c992d8a148; "
            + "Idea-b969b4f=03e5e381-4bf6-4045-a4a3-a6f0a7ab6ced; "
            + "Idea-b969b50=25d9d319-6628-4124-818b-e53f4dd4f9a4; "
            + "_ga=GA1.1.1289063521.1597912649; "
            + "logined=true";

        assertThat(RequestUtils.logined(cookie)).isTrue();
    }
}
