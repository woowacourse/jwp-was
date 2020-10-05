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
        assertThat(ContentType.of(extension)).isInstanceOf(ContentType.class);
    }

    @Test
    @DisplayName("ContentType - extension이 없을 경우")
    void create_IfNoExtension_ReturnNull() {
        assertThat(ContentType.of(null)).isNull();
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
        ContentType contentType = ContentType.of(extension);

        assertThat(contentType.getContentTypeValue()).isEqualTo(contentTypeValue);
    }
}
