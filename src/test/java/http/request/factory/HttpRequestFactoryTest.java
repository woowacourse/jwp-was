package http.request.factory;

import http.HttpMethod;
import http.HttpVersion;
import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.request.HttpRequestHeader;
import http.request.HttpRequestLine;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestFactoryTest {

    @Test
    void create_Body가_있는_경우() {
        HttpRequestLine httpRequestLine = new HttpRequestLine(HttpMethod.POST, "/", HttpVersion.HTTP_1_1);

        Map<String, String> map = new HashMap<>();
        map.put("Host", "localhost:8080");
        map.put("Connection", "keep-alive");
        map.put("Content-Length", "345");
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(map);

        List<String> bodyLines = Arrays.asList("body line1", "body line2");
        HttpRequestBody httpRequestBody = new HttpRequestBody(bodyLines);

        List<String> lines = Arrays.asList(
                "POST / HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 345",
                "",
                "body line1",
                "body line2"
        );

        assertThat(HttpRequestFactory.create(lines)).isEqualTo(new HttpRequest(httpRequestLine, httpRequestHeader, httpRequestBody));
    }

    @Test
    void create_Body가_없는_경우() {
        HttpRequestLine httpRequestLine = new HttpRequestLine(HttpMethod.GET, "/", HttpVersion.HTTP_1_1);

        Map<String, String> map = new HashMap<>();
        map.put("Host", "localhost:8080");
        map.put("Connection", "keep-alive");
        map.put("Content-Length", "345");
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(map);

        HttpRequestBody httpRequestBody = new HttpRequestBody(new ArrayList<>());

        List<String> lines = Arrays.asList(
                "GET / HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 345"
        );

        assertThat(HttpRequestFactory.create(lines)).isEqualTo(new HttpRequest(httpRequestLine, httpRequestHeader, httpRequestBody));
    }
}