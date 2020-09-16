package webserver.requestmapping;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import exception.FileNotSupportedException;
import http.ContentType;

class FileExtensionTypeTest {

    @ParameterizedTest
    @CsvSource({"example.ttf,true", "example.asdf,false", "asdfjs,false"})
    void isSupportedExtension(String fileName, boolean expected) {
        boolean isSupported = FileExtensionType.isSupportedFile(fileName);
        assertThat(isSupported).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "/example.ttf,./static/example.ttf",
        "/example.html,./templates/example.html"
    })
    void findLocalPath(String fileName, String expected) {
        String output = FileExtensionType.findLocalPath(fileName);
        assertThat(output).isEqualTo(expected);
    }

    @Test
    void findLocalPathThrowingException() {
        assertThatThrownBy(() -> FileExtensionType.findLocalPath("NOT_SUPPORTED_FILE"))
            .isInstanceOf(FileNotSupportedException.class);
    }

    @ParameterizedTest
    @CsvSource({
        "/example.ttf,TTF",
        "/example.html,HTML",
        "/not.supoorted,PLAIN"
    })
    void findMatchingContentType(String fileName, ContentType expected) {
        ContentType output = FileExtensionType.findMatchingContentType(fileName);
        assertThat(output).isEqualTo(expected);
    }
}