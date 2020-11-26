package utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StaticResourceMatcherTest {
    @DisplayName("static resource 확인")
    @ParameterizedTest
    @CsvSource(value = {"/css/style.css:true", "/js/scripts.js:true", "/index.html:true", "/js/bootstrap.min.js:true",
        "/user/create:false", "/css%/style.css:false", "?/js/scripts.js:false"}, delimiterString = ":")
    void isStaticResource(String path, boolean value) {
        assertThat(StaticResourceMatcher.isStaticResourcePath(path)).isEqualTo(value);
    }
}