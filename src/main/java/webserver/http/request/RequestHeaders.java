package webserver.http.request;

import java.util.HashMap;
import java.util.Map;

public class RequestHeaders {
    private final Map<String, String> headers;

    RequestHeaders() {
        headers = new HashMap<>();
    }

    RequestHeaders(final Map<String, String> headers) {
        this.headers = headers;
    }

    void addHeader(final String name, final String value) {
        headers.put(name, value);
    }

    String getHeader(final String name) {
        return headers.get(name);
    }

    int size() {
        return headers.size();
    }

    public boolean contains(final String name) {
        return headers.containsKey(name);
    }
}
