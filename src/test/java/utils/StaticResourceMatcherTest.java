package utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StaticResourceMatcherTest {

    @Test
    void isStaticResource() {
        String css = "/css/style.css";
        String js = "/js/scripts.js";
        String html = "/index.html";
        String createUser = "/user/create";
        String wrongCSS = "/css./style.css";
        String wrongJs = "?/js/scripts.js";

        assertAll(
            () -> assertThat(StaticResourceMatcher.isStaticResourcePath(css)).isEqualTo(true),
            () -> assertThat(StaticResourceMatcher.isStaticResourcePath(js)).isEqualTo(true),
            () -> assertThat(StaticResourceMatcher.isStaticResourcePath(html)).isEqualTo(true),
            () -> assertThat(StaticResourceMatcher.isStaticResourcePath(createUser)).isEqualTo(false),
            () -> assertThat(StaticResourceMatcher.isStaticResourcePath(wrongCSS)).isEqualTo(false),
            () -> assertThat(StaticResourceMatcher.isStaticResourcePath(wrongJs)).isEqualTo(false)
        );
    }
}