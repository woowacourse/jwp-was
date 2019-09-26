package webserver.http.httpRequest;

import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;

import static org.assertj.core.api.Assertions.assertThat;

class HttpStartLineTest {

    @Test
    void HttpStartLine생성및_GET_메서드_확인() {
        String startLine = "GET / HTTP1.1";
        HttpStartLine httpStartLine = HttpStartLine.create(startLine);
        assertThat(httpStartLine.checkMethod(HttpMethod.GET)).isTrue();
    }

    @Test
    void HttpStartLine생성및_POST_메서드_확인() {
        String startLine = "POST / HTTP1.1";
        HttpStartLine httpStartLine = HttpStartLine.create(startLine);
        assertThat(httpStartLine.checkMethod(HttpMethod.POST)).isTrue();
    }

    @Test
    void Path생성_확인() {
        String startLine = "POST /index.html HTTP1.1";
        HttpStartLine httpStartLine = HttpStartLine.create(startLine);
        assertThat(httpStartLine.getPath()).isEqualTo("/index.html");
    }

    @Test
    void 파라미터_확인() {
        String startLine = "POST /index.html?name=java HTTP1.1";
        HttpStartLine httpStartLine = HttpStartLine.create(startLine);
        assertThat(httpStartLine.getParam("name")).isEqualTo("java");
    }
}