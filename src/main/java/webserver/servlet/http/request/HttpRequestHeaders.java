package webserver.servlet.http.request;

import java.util.Map;

class HttpRequestHeaders {

    private final Map<String, String> headers;

    public HttpRequestHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String get(String key) {
        return headers.get(key);
    }
}
