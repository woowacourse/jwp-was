package http.request.factory;

import http.request.HttpRequestHeader;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestHeaderFactoryTest {

    @Test
    void create() {
        Map<String, String> map = new HashMap<>();
        map.put("Host", "localhost:8080");
        map.put("Connection", "keep-alive");
        map.put("Content-Length", "345");
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(map);

        List<String> lines = Arrays.asList("Host: localhost:8080", "Connection: keep-alive", "Content-Length: 345");

        assertThat(HttpRequestHeaderFactory.create(lines)).isEqualTo(httpRequestHeader);
    }
}