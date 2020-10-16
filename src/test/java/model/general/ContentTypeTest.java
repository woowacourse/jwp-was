package model.general;

import static org.assertj.core.api.Assertions.assertThat;

import jdk.nashorn.internal.ir.RuntimeNode.Request;
import model.request.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ContentTypeTest {

    @ParameterizedTest
    @DisplayName("ContentType 생성")
    @ValueSource(strings = {".html", ".css", ".js", ".woff", ".ttf", ".ico"})
    void create(String extension) {
        ContentType contentType = ContentType.of(extension)
            .orElse(null);
        assertThat(contentType).isInstanceOf(ContentType.class);
    }

    @Test
    @DisplayName("ContentType 생성 - 확장자가 유효하지 않을 경우")
    void create_IfInvalidExtension_ReturnNull() {
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
