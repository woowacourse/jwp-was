package web;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StaticFileTest {

    @DisplayName("지원하는 정적파일 찾기")
    @ParameterizedTest
    @CsvSource(value = {"/fonts/glyphicons-halflings-regular.woff, text/woff", "/css/bootstrap.min.css, text/css",
        "/index.html, text/html", "/js/bootstrap.min.js, text/javascript"})
    void findStaticFile(String requestPath, String contentType) {
        StaticFile staticFile = StaticFile.findStaticFile(requestPath);
        assertThat(staticFile.getContentType()).isEqualTo(contentType);
    }

    @DisplayName("지원하지 않는 정적파일 찾을 시 예외 발생")
    @Test
    void unsupported_StaticFile_Throw_IllegalArgumentException() {
        String requestPath = "/static/images/dog.gif";

        assertThatThrownBy(() -> StaticFile.findStaticFile(requestPath))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(String.format("%s : 지원하지 않는 정적 파일 타입입니다.", requestPath));
    }
}
