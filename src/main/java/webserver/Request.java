package webserver;

import java.util.Map;

public class Request {

    private final String method;
    private final String url;
    private final Map<String, String> queries;
    private final Map<String, String> headers;
    private final byte[] body;

    public Request(String method, String url, Map<String, String> queries, Map<String, String> headers, byte[] body) {
        this.method = method;
        this.url = url;
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

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public byte[] getBody() {
        return body;
    }
}
