package http;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class StaticFilesTest {

    @DisplayName("정적파일이 위치한 디렉토리를 반환한다.")
    @CsvSource(value = {"index.html,./templates", "styles.css,./static/css"})
    @ParameterizedTest
    void getDirectoryEndsWith(String staticFile, String directory) {
        assertThat(directory).isEqualTo(StaticFiles.getDirectoryEndsWith(staticFile));
    }

    @DisplayName("정적파일 확장자가 올바르지 않을 경우 예외처리한다.")
    @ValueSource(strings = {"index.woowa", "styles.scv"})
    @ParameterizedTest
    void getDirectoryEndsWithThrowsException(String wrongStaticFile) {
        assertThatThrownBy(() -> StaticFiles.getDirectoryEndsWith(wrongStaticFile))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정적파일 확장자가 올바른 경우 true, 아닌 경우 false를 리턴한다.")
    @CsvSource(value = {"index.html,true", "index.woowa,false"})
    @ParameterizedTest
    void endsWith(String staticFile, boolean expected) {
        assertThat(expected).isEqualTo(StaticFiles.endsWith(staticFile));
    }
}