package http.request.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.StringUtils.*;

class RequestPrefixPathTest {
    private RequestPrefixPath requestPrefixPath;

    @Test
    @DisplayName("정적 파일을 제대로 파싱하는지 테스트한다.")
    void static_path() {
        requestPrefixPath = RequestPrefixPath.of(CSS_PATH);
        assertThat(requestPrefixPath.getPathPrefix()).isEqualTo("../resources/static");
    }

    @Test
    @DisplayName("동적 파일을 제대로 파싱하는지 테스트한다.")
    void dynamic_path() {
        requestPrefixPath = RequestPrefixPath.of(FILE_PATH);
        assertThat(requestPrefixPath.getPathPrefix()).isEqualTo("../resources/templates");
    }
}