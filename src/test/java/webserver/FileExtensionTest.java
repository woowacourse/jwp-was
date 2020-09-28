package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class FileExtensionTest {

    @DisplayName("확장자로 static 디렉토리인지 판단")
    @ParameterizedTest
    @CsvSource(value = {"/index.html, false", "/css.css, true"})
    void isStaticFile(String uri, boolean expected) {
        assertThat(FileExtension.find(uri).isStaticDirectory()).isEqualTo(expected);
    }
}
