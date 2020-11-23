package kr.wootecat.dongle.model.http.request;

import kr.wootecat.dongle.model.http.HttpMethod;

public class HttpRequest {

    private final HttpRequestLine requestLine;
    private final HttpRequestHeaders requestHeaders;
    private final HttpRequestBody requestBody;
    private final boolean isParsedSuccessfully;

    public HttpRequest(HttpRequestLine requestLine, HttpRequestHeaders requestHeaders,
            HttpRequestBody requestBody) {
        this(requestLine, requestHeaders, requestBody, true);
    }

    public HttpRequest(HttpRequestLine requestLine, HttpRequestHeaders requestHeaders,
            HttpRequestBody requestBody, boolean isParsedSuccessfully) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
        this.isParsedSuccessfully = isParsedSuccessfully;
    }

    public static HttpRequest ofInternalError() {
        return new HttpRequest(HttpRequestLine.internalErrorPage(), HttpRequestHeaders.empty(),
                HttpRequestBody.empty(), false);
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
        return requestBody.get(name);
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

    public boolean isParsedSuccessfully() {
        return isParsedSuccessfully;
    }
}
