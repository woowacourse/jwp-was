package utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

class HttpRequestUtilsTest {

    @Test
    public void extractPath() {
        String path = HttpRequestUtils.extractPath("GET /index.html HTTP/1.1");

        assertThat(path).isEqualTo("/index.html");
    }

    @Test
    public void parseQueryString() {
        Map<String, String> params = HttpRequestUtils.parseQueryString("/user/create?userId=bsdg&password=password&name=ys&email=test@test.com");

        assertAll(
            () -> assertThat(params).containsEntry("userId", "bsdg"),
            () -> assertThat(params).containsEntry("password", "password"),
            () -> assertThat(params).containsEntry("name", "ys"),
            () -> assertThat(params).containsEntry("email", "test@test.com")
        );
    }

}