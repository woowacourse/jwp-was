package webserver.httpRequest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpStartLineTest {

    @Test
    void HttpStartLine생성및_GET_메서드_확인() {
        String startLine = "GET / HTTP1.1";
        HttpStartLine httpStartLine = HttpStartLine.of(startLine);
        assertThat(httpStartLine.isGet()).isTrue();
    }

    @Test
    void HttpStartLine생성및_POST_메서드_확인() {
        String startLine = "POST / HTTP1.1";
        HttpStartLine httpStartLine = HttpStartLine.of(startLine);
        assertThat(httpStartLine.isPost()).isTrue();
    }

    @Test
    void Path생성_확인() {
        String startLine = "POST /index.html HTTP1.1";
        HttpStartLine httpStartLine = HttpStartLine.of(startLine);
        assertThat(httpStartLine.getPath()).isEqualTo("/index.html");
    }

    @Test
    void 파라미터_확인() {
        String startLine = "POST /index.html?name=java HTTP1.1";
        HttpStartLine httpStartLine = HttpStartLine.of(startLine);
        assertThat(httpStartLine.getParam("name")).isEqualTo("java");
    }
}