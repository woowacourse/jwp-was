package model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ContentTypeTest {

    @ParameterizedTest
    @DisplayName("ContentType of")
    @ValueSource(strings = {".html", ".css", ".js", ".woff", ".ttf", ".ico"})
    void create(String extension) {
        ContentType contentType = ContentType.of(extension)
            .orElse(null);
        assertThat(contentType).isInstanceOf(ContentType.class);
    }

    @Test
    @DisplayName("ContentType - extension을 알 수 없는 경우")
    void create_IfNoExtension_ReturnNull() {
        ContentType contentType = ContentType.of("unknown")
            .orElse(null);
        assertThat(contentType).isNull();
    }

    @ParameterizedTest
    @DisplayName("ContentTypeValue 확인")
    @CsvSource(value = {
        ".html:text/html",
        ".css:text/css",
        ".js:text/javascript",
        ".woff:text/font",
        ".ttf:text/font",
        ".ico:image/x-icon"
    }, delimiter = ':')
    void getContentType(String extension, String contentTypeValue) {
        ContentType contentType = ContentType.of(extension)
            .orElse(null);

        assertThat(contentType.getContentTypeValue()).isEqualTo(contentTypeValue);
    }
}
