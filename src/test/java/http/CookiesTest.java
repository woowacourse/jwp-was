package http;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.request.HttpRequestHeader;
import http.request.HttpRequestLine;
import http.request.HttpRequestUrl;

class CookiesTest {

    @Test
    void isEmpty_False() {
        // given
        final HttpRequestLine httpRequestLine = new HttpRequestLine("GET", new HttpRequestUrl("/index.html"), "HTTP/1.1");
        final Map<String, String> header = new HashMap<>();
        header.put("Cookie", "Path=/");
        final HttpRequestHeader httpRequestHeader = new HttpRequestHeader(header);
        final HttpRequest httpRequest = new HttpRequest(httpRequestLine, httpRequestHeader, HttpRequestBody.emptyBody());

        // when
        Cookies cookies = new Cookies(httpRequest);

        // then
        assertThat(cookies.isEmpty()).isFalse();
    }

    @Test
    void isEmpty_True() {
        // given
        final HttpRequestLine httpRequestLine = new HttpRequestLine("GET", new HttpRequestUrl("/index.html"), "HTTP/1.1");
        final HttpRequestHeader httpRequestHeader = new HttpRequestHeader(new HashMap<>());
        final HttpRequest httpRequest = new HttpRequest(httpRequestLine, httpRequestHeader, HttpRequestBody.emptyBody());

        // when
        Cookies cookies = new Cookies(httpRequest);

        // then
        assertThat(cookies.isEmpty()).isTrue();
    }

    @Test
    void flat() {
        // given
        final HttpRequestLine httpRequestLine = new HttpRequestLine("GET", new HttpRequestUrl("/index.html"), "HTTP/1.1");
        final HttpRequestHeader httpRequestHeader = new HttpRequestHeader(new HashMap<>());
        final HttpRequest httpRequest = new HttpRequest(httpRequestLine, httpRequestHeader, HttpRequestBody.emptyBody());
        Cookies cookies = new Cookies(httpRequest);
        cookies.addCookie("logined", true);
        cookies.addCookie("Path", "/");

        // when
        final String flatCookie = cookies.flat();

        // then
        assertThat(flatCookie).isEqualTo("logined=true; Path=/");
    }
}