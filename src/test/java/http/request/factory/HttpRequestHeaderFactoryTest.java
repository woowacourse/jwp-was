package http.request.factory;

import http.request.HttpCookieStore;
import http.request.HttpRequestHeader;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestHeaderFactoryTest {

    @Test
    void create() {
        Map<String, String> map = new HashMap<>();
        map.put("Host", "localhost:8080");
        map.put("Connection", "keep-alive");
        map.put("Content-Length", "345");
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(map, new HttpCookieStore(Collections.EMPTY_LIST));

        List<String> lines = Arrays.asList("Host: localhost:8080", "Connection: keep-alive", "Content-Length: 345");

        assertThat(HttpRequestHeaderFactory.createHttpCookieStore(lines)).isEqualTo(httpRequestHeader);
    }
}