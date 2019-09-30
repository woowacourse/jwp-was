package dev.luffy.utils;

import dev.luffy.http.request.HttpRequestUrl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UrlParameterParserTest {

    @DisplayName("Query Parameter 가 없는 HttpRequestUrl 객체를 파싱한다.")
    @Test
    void parseHttpRequestUrlWithNoQueryParams() {
        HttpRequestUrl httpRequestUrl = HttpRequestUrl.of("/index.html");
        Map<String, String> queryParams = UrlParameterParser.parse(httpRequestUrl);
        assertTrue(queryParams.isEmpty());
    }

    @DisplayName("Query Parameter 가 비어있는 HttpRequestUrl 객체를 파싱한다.")
    @Test
    void parseHttpRequestUrlWithEmptyQueryParams() {
        HttpRequestUrl httpRequestUrl = HttpRequestUrl.of("/index.html?");
        Map<String, String> queryParams = UrlParameterParser.parse(httpRequestUrl);
        assertTrue(queryParams.isEmpty());
    }

    @DisplayName("Query Parameter 가 하나인 HttpRequestUrl 객체를 파싱한다.")
    @Test
    void parseHttpRequestUrlWithOneQueryParameterPair() {
        HttpRequestUrl httpRequestUrl = HttpRequestUrl.of("/index?a=0705");
        Map<String, String> queryParams = UrlParameterParser.parse(httpRequestUrl);
        assertEquals(queryParams.get("a"), "0705");
    }

    @DisplayName("Query Parameter 가 두개인 HttpRequestUrl 객체를 파싱한다.")
    @Test
    void parseHttpRequestUrlWithTwoQueryParameterPairs() {
        HttpRequestUrl httpRequestUrl = HttpRequestUrl.of("/index?a=0705&b=hello");
        Map<String, String> queryParams = UrlParameterParser.parse(httpRequestUrl);
        assertEquals(queryParams.get("a"), "0705");
        assertEquals(queryParams.get("b"), "hello");
    }
}
