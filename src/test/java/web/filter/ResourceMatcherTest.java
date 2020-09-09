package web.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ResourceMatcherTest {

    @DisplayName("확장자로 matcher 찾기")
    @ParameterizedTest
    @CsvSource(value = {"a.html,HTML", "a.js,JAVASCRIPT", "a.css,CSS"})
    void findResourceFilter(String requestPath, ResourceMatcher resourceMatcher) {
        ResourceMatcher result = ResourceMatcher.findResourceMatcher(requestPath);

        assertThat(result).isEqualTo(resourceMatcher);
    }

    @DisplayName("정적 정보가 아닌데 파일을 찾으려 하는 경우 Exception")
    @Test
    void findResourceFilterException() {
        String requestPath = "/user/create";

        assertThatThrownBy(() -> ResourceMatcher.findResourceMatcher(requestPath))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("%s :일치하는 확장자가 존재하지 않습니다.", requestPath);
    }

    @DisplayName("확장자로 정적 자원인지 판단")
    @ParameterizedTest
    @CsvSource(value = {"a.html,true", "/user/create,false"})
    void isResource(String requestPath, boolean expect) {
        boolean result = ResourceMatcher.isResource(requestPath);

        assertThat(result).isEqualTo(expect);
    }

}