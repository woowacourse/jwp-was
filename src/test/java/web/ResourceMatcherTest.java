package web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceMatcherTest {

    @DisplayName("경로가 정적 파일을 요청 하는 요청인지 확인할 수 있다.")
    @CsvSource({"/index.html, true", "./css/styles.css, true", "./js/scripts.js, true", "/user, false"})
    @ParameterizedTest
    void isStaticFile(String path, boolean expectedResult) {
        boolean isStaticFile = ResourceMatcher.isStaticFile(path);
        assertThat(isStaticFile).isEqualTo(expectedResult);
    }


    @DisplayName("정적 파일을 요청하는 경우 정적 파일이 위치하는 baseDirectory를 받아 온다.")
    @CsvSource({"/index.html, ./templates", "./css/styles.css, ./static", "./js/scripts.js, ./static", "/user, /"})
    @ParameterizedTest
    void findBaseDirectory(String path, String expectedBaseDirectory) {
        String baseDirectory = ResourceMatcher.findBaseDirectory(path);
        assertThat(baseDirectory).isEqualTo(expectedBaseDirectory);
    }
}