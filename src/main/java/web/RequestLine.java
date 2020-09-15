package web;

import utils.HttpRequestUtils;

public class RequestLine {

    private HttpMethod method;
    private String path;
    private String version;

    private RequestLine(HttpMethod method, String path, String version) {
        this.method = method;
        this.path = path;
        this.version = version;
    }

    public static RequestLine of(String line) {
        return new RequestLine(
            HttpRequestUtils.extractHttpMethod(line),
            HttpRequestUtils.extractPath(line),
            HttpRequestUtils.extractVersion(line)
        );
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }
}
