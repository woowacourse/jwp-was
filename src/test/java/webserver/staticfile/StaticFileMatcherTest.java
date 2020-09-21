package webserver.staticfile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class StaticFileMatcherTest {

    @DisplayName("정적 파일에 대한 요청일 때")
    @ParameterizedTest
    @CsvSource({"/index.html,true", "/user/create,false"})
    void staticReqTest(String resourcePath, boolean expected) {
        boolean actual = StaticFileMatcher.isStaticFileResourcePath(resourcePath);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("resourcePath로 정적 파일을 찾는다.")
    @ParameterizedTest
    @CsvSource({"/index.html,HTML", "/css/new.css,CSS", "/js/my.js,JS"})
    void findStaticFileTypeTest(String resourcePath, String type) {
        StaticFileType actual = StaticFileMatcher.findStaticFileType(resourcePath);

        StaticFileType expected = StaticFileType.valueOf(type);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("resourcePath에 대한 실제 경로를 찾느다.")
    @ParameterizedTest
    @CsvSource({"/index.html,./templates/index.html",
            "/css/new.css,./static/css/new.css",
            "/js/my.js,./static/js/my.js",})
    void name(String resourcePath, String expected) {
        String actual = StaticFileMatcher.findStaticFilePath(resourcePath);
        assertThat(actual).isEqualTo(expected);
    }
}