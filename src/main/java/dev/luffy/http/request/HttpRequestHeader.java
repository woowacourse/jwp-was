package dev.luffy.http.request;

import java.util.Map;

import dev.luffy.http.excption.NotFoundHeaderException;

public class HttpRequestHeader {

    private static final String NOT_FOUND_HEADER_MESSAGE = "그런 헤더는 존재하지 않습니다.";
    private static final String CONTENT_LENGTH = "Content-Length";

    private final Map<String, String> headers;

    public HttpRequestHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public String get(String key) {
        checkContainsKey(key);
        return headers.get(key);
    }

    private void checkContainsKey(String key) {
        if (!headers.containsKey(key)) {
            throw new NotFoundHeaderException(NOT_FOUND_HEADER_MESSAGE);
        }
    }

    public int getContentLength() {
        if (headers.containsKey(CONTENT_LENGTH)) {
            return Integer.parseInt(headers.get(CONTENT_LENGTH));
        }
        return 0;
    }

    public boolean hasCookie() {
        return headers.containsKey("Cookie");
    }

    @Override
    public String toString() {
        return "HttpRequestHeader{" +
                "headers=" + headers +
                '}';
    }
}
