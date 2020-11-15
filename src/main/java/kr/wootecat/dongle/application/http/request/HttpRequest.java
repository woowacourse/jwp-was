package kr.wootecat.dongle.application.http.request;

import kr.wootecat.dongle.application.http.HttpMethod;

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

    public boolean hasCookie(String value, boolean name) {
        return requestHeaders.containsCookie(value, name);
    }
}
