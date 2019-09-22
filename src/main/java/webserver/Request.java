package webserver;

import java.util.Map;

public class Request {

    private final String method;
    private final String path;
    private final Map<String, String> queries;
    private final Map<String, String> headers;
    private final Map<String, String> cookies;
    private final byte[] body;

    public Request(String method, String path, Map<String, String> queries, Map<String, String> headers, Map<String, String> cookies, byte[] body) {
        this.method = method;
        this.path = path;
        this.queries = queries;
        this.headers = headers;
        this.cookies = cookies;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getQuery(String key) {
        return queries.get(key);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public byte[] getBody() {
        return body;
    }
}
