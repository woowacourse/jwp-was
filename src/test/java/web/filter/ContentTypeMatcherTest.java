package web.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.ContentTypeMatcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContentTypeMatcherTest {

    @DisplayName("확장자로 matcher 찾기")
    @ParameterizedTest
    @CsvSource(value = {"a.html,text/html;charset=utf-8", "a.js,application/javascript;charset=utf-8", "a.css,text/css;charset=utf-8"})
    void findResourceFilter(String requestPath, String resourceMatcher) {
        String result = ContentTypeMatcher.findResourceContentType(requestPath);

        assertThat(result).isEqualTo(resourceMatcher);
    }

    @DisplayName("정적 정보가 아닌데 파일을 찾으려 하는 경우 Exception")
    @Test
    void findResourceFilterException() {
        String requestPath = "/user/create";

        assertThatThrownBy(() -> ContentTypeMatcher.findResourceContentType(requestPath))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("%s :일치하는 확장자가 존재하지 않습니다.", requestPath);
    }


}