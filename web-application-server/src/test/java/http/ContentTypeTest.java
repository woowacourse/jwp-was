package http;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class ContentTypeTest {

    @DisplayName("Content Type을 생성한다.")
    @ParameterizedTest
    @CsvSource(value = {"/hello.css:text/css;charset=utf-8", "/hello.CSS:text/css;charset=utf-8",
        "/hello.js:text/javascript;charset=utf-8", "/hello.JS:text/javascript;charset=utf-8",
        "/hello.html:text/html;charset=utf-8", "/hello.HTML:text/html;charset=utf-8"}, delimiterString = ":")
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