package webserver.http.request;

import java.util.HashMap;
import java.util.Map;

import webserver.http.HttpMethod;

class HttpRequestLine {
    private final HttpMethod method;
    private final String path;
    private final String protocol;
    private final Map<String, String> queryParameters = new HashMap<>();

    public HttpRequestLine(HttpMethod method, String path, String protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public boolean isStaticResourceRequest() {
        final int extensionIndex = path.lastIndexOf(".");
        return method.isGet() && extensionIndex != -1 && path.substring(extensionIndex + 1)
                .matches("html|ico|css|js|eot|svg|woff|woff2|png|ttf");
    }

    public String getPath() {
        return this.path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public boolean isGetMethod() {
        return method.isGet();
    }
}
