package utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class ContentTypeTest {

    @DisplayName("Content Type을 생성한다.")
    @ParameterizedTest
    @CsvSource(value = {"/hello.css:text/css", "/hello.CSS:text/css", "/hello.js:text/javascript",
        "/hello.JS:text/javascript", "/hello.html:text/html", "/hello.HTML:text/html"}, delimiterString = ":")
    void contentType(String uri, String contentType) {
        ContentType extracted = ContentType.findByURI(uri);

        assertThat(extracted.getContentType()).isEqualTo(contentType);
    }

    @DisplayName("Content Type 생성 예외")
    @ParameterizedTest
    @NullAndEmptySource
    void contentTypeException(String uri) {
        assertThatThrownBy(() -> ContentType.findByURI(uri))
            .isInstanceOf(IllegalArgumentException.class);
    }
}