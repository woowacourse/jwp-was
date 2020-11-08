package webserver.http.request;

import java.util.Map;

class HttpRequestHeader {
    private final Map<String, String> headers;

    public HttpRequestHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public String get(String key) {
        return headers.get(key);
    }
}
