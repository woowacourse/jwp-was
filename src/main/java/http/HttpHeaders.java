package http;

import java.util.Map;

public class HttpHeaders {
    private Map<String, String> headers;

    public HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }
}
