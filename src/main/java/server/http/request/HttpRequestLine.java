package server.http.request;

import java.util.Map;

public class HttpRequestLine {
    private final String httpMethod;
    private final String path;
    private final String httpVersion;
    private final Map<String, String> queryParams;

    public HttpRequestLine(String httpMethod, String path, String httpVersion, Map<String, String> queryParams) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.httpVersion = httpVersion;
        this.queryParams = queryParams;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public String getQueryParam(String key) {
        return queryParams.get(key);
    }
}
