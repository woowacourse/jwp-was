package webserver.http.request.core;

import webserver.http.HttpVersion;

public class RequestLine {
    private RequestMethod requestMethod;
    private RequestPath requestPath;
    private HttpVersion httpVersion;

    public RequestLine(RequestMethod requestMethod, RequestPath requestPath, HttpVersion httpVersion) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
        this.httpVersion = httpVersion;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public RequestPath getRequestPath() {
        return requestPath;
    }
}
