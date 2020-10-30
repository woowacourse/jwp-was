package web.http;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseHeader {

    private final HttpResponseLine httpResponseLine;
    private final Map<String, Object> headers;

    public HttpResponseHeader(HttpResponseLine httpResponseLine, Map<String, Object> headers) {
        this.httpResponseLine = httpResponseLine;
        this.headers = headers;
    }

    public HttpResponseHeader(HttpResponseLine httpResponseLine) {
        this(httpResponseLine, new HashMap<>());
    }

    public void add(String key, Object value) {
        headers.put(key, value);
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public HttpResponseLine getHttpResponseLine() {
        return httpResponseLine;
    }
}
