package webserver;

import java.util.Map;

public class HttpRequest {

    private final HttpMethod method;
    private final String url;
    private final String path;
    private final Map<String, String> queries;
    private final Map<String, String> headers;
    private final byte[] body;

    public HttpRequest(HttpMethod method, String url, String path, Map<String, String> queries, Map<String, String> headers, byte[] body) {
        this.method = method;
        this.url = url;
        this.path = path;
        this.queries = queries;
        this.headers = headers;
        this.body = body;
    }

    public String getQuery(String key) {
        return queries.get(key);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }

    public byte[] getBody() {
        return body;
    }
}
