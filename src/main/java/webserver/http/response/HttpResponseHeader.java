package webserver.http.response;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseHeader {
    private final HttpResponseLine httpResponseLine;
    private final Map<String, String> headers;

    public HttpResponseHeader(HttpResponseLine httpResponseLine, Map<String, String> headers) {
        this.httpResponseLine = httpResponseLine;
        this.headers = headers;
    }

    public HttpResponseHeader(HttpResponseLine httpResponseLine) {
        this(httpResponseLine, new HashMap<>());
    }

    public void add(String key, String value) {
        headers.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpResponseLine getHttpResponseLine() {
        return httpResponseLine;
    }
}
