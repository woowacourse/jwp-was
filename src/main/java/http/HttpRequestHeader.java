package http;

import java.util.Map;

import http.excption.NotFoundHeaderException;

public class HttpRequestHeader {

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
            throw new NotFoundHeaderException("그런 헤더는 존재하지 않습니다.");
        }
    }
}
