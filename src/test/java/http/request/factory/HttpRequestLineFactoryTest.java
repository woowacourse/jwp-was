package http.request.factory;

import http.HttpMethod;
import http.HttpVersion;
import http.request.HttpRequestLine;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestLineFactoryTest {

    @Test
    void create() {
        HttpRequestLine httpRequestLine = new HttpRequestLine(HttpMethod.GET, "/", HttpVersion.HTTP_1_1);
        assertThat(HttpRequestLineFactory.create("GET / HTTP/1.1")).isEqualTo(httpRequestLine);
    }
}