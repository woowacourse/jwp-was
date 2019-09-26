package http.request;

import http.NotSupportedHttpMethodException;
import http.parameter.Parameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpRequestStartLineTest {

    @Test
    @DisplayName("이상한 입력")
    void wrongStartLine() {
        String wrongStartLine = "wrong startLine";

        assertThrows(InvalidHttpRequestStartLine.class, () -> HttpRequestStartLine.of(wrongStartLine));
    }

    @Test
    @DisplayName("지원하지 않는 메소드 타입")
    void notSupportedHttpMethod() {
        String startLineOfNotSupportedMethod = "NOT_SUPPORTED /index.html HTTP/1.1\r\n";

        assertThrows(NotSupportedHttpMethodException.class, () -> HttpRequestStartLine.of(startLineOfNotSupportedMethod));
    }

    @Test
    @DisplayName("파라미터 존재하지 않는 경우")
    void noParameters() {
        String startLineWithoutParameters = "GET /index.html HTTP/1.1\r\n";

        HttpRequestStartLine startLine = HttpRequestStartLine.of(startLineWithoutParameters);

        assertThat(startLine.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(startLine.getPath()).isEqualTo("/index.html");
        assertThat(startLine.getParameters()).isEqualTo(Parameters.EMPTY_PARAMETERS);
    }

    @Test
    @DisplayName("파라미터 존재하는 경우")
    void hasParameters() {
        String startLineWithoutParameters = "GET /index.html?param=exist HTTP/1.1\r\n";

        HttpRequestStartLine startLine = HttpRequestStartLine.of(startLineWithoutParameters);

        assertThat(startLine.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(startLine.getPath()).isEqualTo("/index.html");
        assertThat(startLine.getParameters()).isEqualTo(Parameters.fromQueryString("param=exist"));
    }
}