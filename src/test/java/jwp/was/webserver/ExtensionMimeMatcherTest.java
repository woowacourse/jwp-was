package jwp.was.webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class ExtensionMimeMatcherTest {

    @DisplayName("MimeType을 구한다 - Mime 타입 반환, 매치된 확장자가 있는 경우")
    @ParameterizedTest
    @CsvSource(value = {"/index.html, text/html", "/index.css, text/css",
        "/js/index.js, application/javascript", "/static/index.woff, font/woff"})
    void getMimeType_NotExistsExtension_ReturnDefaultMime(String urlPath, String expectedMimeType) {
        String mimeType = ExtensionMimeMatcher.getMimeType(urlPath);

        assertThat(mimeType).isEqualTo(expectedMimeType);
    }

    @DisplayName("MimeType을 구한다 - 기본 Mime 타입 반환, 매치되는 확장자가 없는 경우")
    @ParameterizedTest
    @ValueSource(strings = {"/index.abc", "/index.def", "/css/index.abc"})
    void getMimeType_NotMatchedExtension_ReturnDefaultMime(String urlPath) {
        String mimeType = ExtensionMimeMatcher.getMimeType(urlPath);

        assertThat(mimeType).isEqualTo("application/octet-stream");
    }

    @DisplayName("MimeType을 구한다 - 예외 발생, 확장자가 없는 경우")
    @ParameterizedTest
    @ValueSource(strings = {"/index", "/static/index", "/css/index"})
    void getMimeType_NoExtension_ThrownException(String urlPath) {
        assertThatThrownBy(() -> ExtensionMimeMatcher.getMimeType(urlPath))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
