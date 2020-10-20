package webserver.http.request;

import java.util.Map;

public class HttpHeader {
    private final HttpMethod method;
    private final RequestURI requestURI;
    private final HttpVersion version;
    private final Map<String, String> fields;

    public HttpHeader(HttpMethod method, RequestURI requestURI, HttpVersion version,
            Map<String, String> fields) {
        this.method = method;
        this.requestURI = requestURI;
        this.version = version;
        this.fields = fields;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public RequestURI getRequestURI() {
        return requestURI;
    }

    public HttpVersion getVersion() {
        return version;
    }

    public String get(String key) {
        return fields.get(key);
    }
}
