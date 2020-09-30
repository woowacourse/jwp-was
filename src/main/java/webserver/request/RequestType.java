package webserver.request;

import webserver.HttpMethod;

public class RequestType {

    private HttpMethod method;
    private String path;

    private RequestType(HttpMethod method, String path) {
        this.method = method;
        this.path = path;
    }

    public static RequestType of(HttpMethod method, String path) {
        return new RequestType(method, path);
    }

    public boolean isGet() {
        return method.isGet();
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }
}
