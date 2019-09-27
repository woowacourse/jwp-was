package webserver.http.httpRequest;

import org.junit.jupiter.api.Test;
import webserver.WebTestForm;
import webserver.http.HttpMethod;

import static org.assertj.core.api.Assertions.assertThat;

class HttpStartLineTest extends WebTestForm {

    @Test
    void HttpStartLine생성및_GET_메서드_확인() {
        String startLine = getHttpGetStartLine("/");
        HttpStartLine httpStartLine = HttpStartLine.create(startLine);
        assertThat(httpStartLine.checkMethod(HttpMethod.GET)).isTrue();
    }

    @Test
    void HttpStartLine생성및_POST_메서드_확인() {
        String startLine = getHttpPostStartLine("/");
        HttpStartLine httpStartLine = HttpStartLine.create(startLine);
        assertThat(httpStartLine.checkMethod(HttpMethod.POST)).isTrue();
    }

    @Test
    void Path생성_확인() {
        String startLine = getHttpPostStartLine("/index.html");
        HttpStartLine httpStartLine = HttpStartLine.create(startLine);
        assertThat(httpStartLine.getPath()).isEqualTo("/index.html");
    }

    @Test
    void 파라미터_확인() {
        String startLine = getHttpPostStartLine("/index.html?name=java");
        HttpStartLine httpStartLine = HttpStartLine.create(startLine);
        assertThat(httpStartLine.getParam("name")).isEqualTo("java");
    }
}