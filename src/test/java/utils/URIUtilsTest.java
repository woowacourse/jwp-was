package utils;

import exception.InvalidRequestPathException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class URIUtilsTest {
    @ParameterizedTest
    @CsvSource(value = {"/index.html,./templates/index.html", "/style.css,./static/style.css"})
    @DisplayName("유효한 파일 경로를 전달받을 시, 이를 가공하여 반환한다.")
    void getFilePathTest(String path, String expected) {
        Assertions.assertThat(URIUtils.getFilePath(path)).isEqualTo(expected);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("유효하지 않은, 비어있는 파일 경로를 전달받을 시, 예외를 발생시킨다.")
    void getFilePathNullAndEmptyExceptionTest(String invalidPath) {
        Assertions.assertThatThrownBy(() -> URIUtils.getFilePath(invalidPath))
                .isInstanceOf(InvalidRequestPathException.class)
                .hasMessage("Request Path의 값이 올바르지 않습니다");
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", ".", "file.", ".twice"})
    @DisplayName("유효하지 않은경로를 전달받을 시, 예외를 발생시킨다.")
    void getFilePathInvalidExceptionTest(String invalidPath) {
        Assertions.assertThatThrownBy(() -> URIUtils.getFilePath(invalidPath))
                .isInstanceOf(InvalidRequestPathException.class)
                .hasMessage("Request Path의 값이 올바르지 않습니다");
    }
}
