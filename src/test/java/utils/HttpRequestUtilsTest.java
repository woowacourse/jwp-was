package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import http.HttpRequestUrl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpRequestUtilsTest {

    @DisplayName("헤더 내용이 들어있는 String List 를 Map 으로 파싱한다.")
    @Test
    void parsingHeaderString() {
        List<String> lines = Arrays.asList(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*"
        );
        Map<String, String> headers = HttpRequestUtils.parse(lines);
        assertEquals(headers.get("Host"), "localhost:8080");
        assertEquals(headers.get("Connection"), "keep-alive");
        assertEquals(headers.get("Accept"), "*/*");
    }

    @DisplayName("헤더 내용이 없으면 빈 Map 으로 파싱한다.")
    @Test
    void parsingEmptyHeaderString() {
        List<String> lines = Collections.singletonList("");
        Map<String, String> headers = HttpRequestUtils.parse(lines);
        assertTrue(headers.isEmpty());
    }

    @DisplayName("Query Parameter 가 없는 HttpRequestUrl 객체를 파싱한다.")
    @Test
    void parseHttpRequestUrlWithNoQueryParams() {
        HttpRequestUrl httpRequestUrl = HttpRequestUrl.of("/index.html");
        Map<String, String> queryParams = HttpRequestUtils.parse(httpRequestUrl);
        assertTrue(queryParams.isEmpty());
    }

    @DisplayName("Query Parameter 가 비어있는 HttpRequestUrl 객체를 파싱한다.")
    @Test
    void parseHttpRequestUrlWithEmptyQueryParams() {
        HttpRequestUrl httpRequestUrl = HttpRequestUrl.of("/index.html?");
        Map<String, String> queryParams = HttpRequestUtils.parse(httpRequestUrl);
        assertTrue(queryParams.isEmpty());
    }

    @DisplayName("Query Parameter 가 하나인 HttpRequestUrl 객체를 파싱한다.")
    @Test
    void parseHttpRequestUrlWithOneQueryParameterPair() {
        HttpRequestUrl httpRequestUrl = HttpRequestUrl.of("/index?a=0705");
        Map<String, String> queryParams = HttpRequestUtils.parse(httpRequestUrl);
        assertEquals(queryParams.get("a"), "0705");
    }

    @DisplayName("Query Parameter 가 두개인 HttpRequestUrl 객체를 파싱한다.")
    @Test
    void parseHttpRequestUrlWithTwoQueryParameterPairs() {
        HttpRequestUrl httpRequestUrl = HttpRequestUrl.of("/index?a=0705&b=hello");
        Map<String, String> queryParams = HttpRequestUtils.parse(httpRequestUrl);
        assertEquals(queryParams.get("a"), "0705");
        assertEquals(queryParams.get("b"), "hello");

    }
}