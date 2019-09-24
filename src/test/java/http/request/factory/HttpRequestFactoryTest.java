package http.request.factory;

import http.HttpRequestMethod;
import http.HttpVersion;
import http.MediaType;
import http.QueryString;
import http.request.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestFactoryTest {

    @Test
    void create_Body가_있는_경우() {
        HttpRequestTarget httpRequestTarget = new HttpRequestTarget(new Resource("/", "index", MediaType.HTML ), new QueryString(""));
        HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine(HttpRequestMethod.POST, httpRequestTarget, HttpVersion.HTTP_1_1);

        Map<String, String> map = new HashMap<>();
        map.put("Host", "localhost:8080");
        map.put("Connection", "keep-alive");
        map.put("Content-Length", "345");
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(map);

        HttpRequestBody httpRequestBody = new HttpRequestBody("id=2222&pw=1234");

        List<String> lines = Arrays.asList(
                "POST /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 345",
                "",
                "id=2222&pw=1234"
        );

        assertThat(HttpRequestFactory.create(lines)).isEqualTo(new HttpRequest(httpRequestStartLine, httpRequestHeader, httpRequestBody));
    }

    @Test
    void create_Body가_없는_경우() {
        HttpRequestTarget httpRequestTarget = new HttpRequestTarget(new Resource("/", "index", MediaType.HTML ), new QueryString("id=2222&pw=1234"));
        HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine(HttpRequestMethod.GET, httpRequestTarget, HttpVersion.HTTP_1_1);

        Map<String, String> map = new HashMap<>();
        map.put("Host", "localhost:8080");
        map.put("Connection", "keep-alive");
        map.put("Content-Length", "345");
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(map);

        HttpRequestBody httpRequestBody = new HttpRequestBody("");

        List<String> lines = Arrays.asList(
                "GET /index.html?id=2222&pw=1234 HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 345"
        );

        assertThat(HttpRequestFactory.create(lines)).isEqualTo(new HttpRequest(httpRequestStartLine, httpRequestHeader, httpRequestBody));
    }
}