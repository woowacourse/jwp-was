package http.request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeader {
    private final Map<String, String> headers = new HashMap<>();

    public HttpRequestHeader(final Map<String, String> headers) {
        this.headers.putAll(headers);
    }

    public String getHeader(final String key) {
        return headers.get(key);
    }
}
