package http.response;

import http.request.exception.NotFoundHttpRequestHeader;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class HttpResponseHeader {
    private static final String HEADER_SPLITTER = ": ";

    private final Map<String, String> headers;

    private HttpResponseHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpResponseHeader create() {
        return new HttpResponseHeader(new HashMap<>());
    }

    public String getHeader(String key) {
        return Optional.ofNullable(headers.get(key))
                .orElseThrow(() -> new NotFoundHttpRequestHeader(key));
    }

    public boolean contains(String key) {
        return headers.containsKey(key);
    }

    public Set<String> keySet() {
        return headers.keySet();
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }
}
