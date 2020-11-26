package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class FileExtensionTest {

    @DisplayName("확장자로 static 디렉토리인지 판단")
    @ParameterizedTest
    @CsvSource(value = {"/index.html, false", "/css.css, true"})
    void isStaticDirectory(String uri, boolean expected) {
        Assertions.assertThat(FileExtension.find(uri).isStaticDirectory()).isEqualTo(expected);
    }

    @DisplayName("파일 확장자인지 판단")
    @ParameterizedTest
    @CsvSource(value = {"/index.html, true", "/user/create, false"})
    void isFileExtension(String uri, boolean expected) {
        Assertions.assertThat(FileExtension.isFileExtension(uri)).isEqualTo(expected);
    }
}
