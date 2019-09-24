package http.request.factory;

import http.HttpRequestMethod;
import http.HttpVersion;
import http.MediaType;
import http.QueryString;
import http.request.HttpRequestStartLine;
import http.request.HttpRequestTarget;
import http.request.Resource;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestStartLineFactoryTest {

    @Test
    void create() {
        HttpRequestTarget httpRequestTarget = new HttpRequestTarget(new Resource("/", "index", MediaType.HTML ), new QueryString(""));
        HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine(HttpRequestMethod.POST, httpRequestTarget, HttpVersion.HTTP_1_1);
        assertThat(HttpRequestStartLineFactory.create("POST /index.html HTTP/1.1")).isEqualTo(httpRequestStartLine);
    }
}