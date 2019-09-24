package http.request;

import http.HttpRequestMethod;
import http.MediaType;
import http.QueryString;
import http.request.factory.HttpRequestStartLineFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestStartLineTest {

    @Test
    void RequestLine() {
        HttpRequestTarget httpRequestTarget = new HttpRequestTarget(new Resource("/", "index", MediaType.HTML ), new QueryString(""));
        HttpRequestStartLine httpRequestStartLine = HttpRequestStartLineFactory.create("GET /index.html?id=javajigi&pw=1234 HTTP/1.1");

        assertThat(httpRequestStartLine.getHttpRequestMethod()).isEqualTo(HttpRequestMethod.GET);
        assertThat(httpRequestStartLine.getQueryString()).isEqualTo("id=javajigi&pw=1234");
        assertThat(httpRequestStartLine.getUri()).isEqualTo("/index.html");
    }
}