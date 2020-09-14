package webserver;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FileExtensionTypeTest {

    @ParameterizedTest
    @CsvSource({"example.ttf,true", "example.asdf,false", "asdfjs,false"})
    void isSupportedExtension(String fileName, boolean expected) {
        boolean isSupported = FileExtensionType.isSupportedFile(fileName);
        assertThat(isSupported).isEqualTo(expected);
    }
}