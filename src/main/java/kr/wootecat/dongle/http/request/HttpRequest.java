package kr.wootecat.dongle.http.request;

import kr.wootecat.dongle.http.HttpMethod;

public class HttpRequest {

    private final HttpRequestLine requestLine;
    private final HttpRequestHeaders requestHeaders;
    private final HttpRequestParameters requestParameters;

    public HttpRequest(HttpRequestLine requestLine, HttpRequestHeaders requestHeaders,
            HttpRequestParameters requestParameters) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.requestParameters = requestParameters;
    }

    public boolean isStaticResourceRequest() {
        return requestLine.isFileRequest();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public boolean isGetMethod() {
        return requestLine.isGetMethod();
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getParameter(String key) {
        return requestParameters.get(key);
    }

    public boolean hasCookie(String name, boolean value) {
        return requestHeaders.hasCookieWithPair(name, value);
    }

    public boolean hasCookie(String name) {
        return requestHeaders.containsCookie(name);
    }
}
