package http.request;

import type.method.MethodType;

public class HttpRequestLine {

    private final MethodType method;
    private final HttpRequestUrl httpRequestUrl;
    private final String version;

    public HttpRequestLine(final String method, final HttpRequestUrl httpRequestUrl, final String version) {
        this.method = MethodType.find(method);
        this.httpRequestUrl = httpRequestUrl;
        this.version = version;
    }

    public MethodType getMethod() {
        return this.method;
    }

    public String getUrl() {
        return this.httpRequestUrl.getUrl();
    }

    public String getValue(final String name) {
        return this.httpRequestUrl.getValue(name);
    }
}
