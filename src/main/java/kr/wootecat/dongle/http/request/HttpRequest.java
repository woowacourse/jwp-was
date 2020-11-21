package kr.wootecat.dongle.http.request;

import kr.wootecat.dongle.http.HttpMethod;

public class HttpRequest {

    private final HttpRequestLine requestLine;
    private final HttpRequestHeaders requestHeaders;
    private final HttpRequestBody requestParameters;

    public HttpRequest(HttpRequestLine requestLine, HttpRequestHeaders requestHeaders,
            HttpRequestBody requestParameters) {
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

    public String getHeader(String name) {
        return requestHeaders.get(name);
    }

    public String getParameter(String name) {
        if (requestLine.isGetMethod()) {
            return requestLine.getParameter(name);
        }
        return requestParameters.get(name);
    }

    public boolean hasCookie(String name, boolean value) {
        return requestHeaders.hasCookieWithPair(name, value);
    }

    public boolean hasCookie(String name) {
        return requestHeaders.containsCookie(name);
    }

    public String getCookie(String name) {
        return requestHeaders.getCookie(name);
    }

    public String getProtocol() {
        return requestLine.getVersion();
    }
}
