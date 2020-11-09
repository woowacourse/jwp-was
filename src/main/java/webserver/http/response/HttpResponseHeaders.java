package webserver.http.response;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponseHeaders {
    private final Map<String, String> responseHeaders;

    private HttpResponseHeaders(Map<String, String> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public static HttpResponseHeaders ofEmpty() {
        return new HttpResponseHeaders(new LinkedHashMap<>());
    }

    public Map<String, String> getResponseHeaders() {
        return Collections.unmodifiableMap(responseHeaders);
    }

    public void addHeader(String key, String value) {
        responseHeaders.put(key, value);
    }
}
