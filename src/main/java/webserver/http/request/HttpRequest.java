package webserver.http.request;

import webserver.http.response.HttpVersion;

public class HttpRequest {
    private RequestLine requestLine;
    private RequestHeader header;
    private RequestBody body;

    public HttpRequest(RequestLine requestLine, RequestHeader header, RequestBody body) {
        this.requestLine = requestLine;
        this.header = header;
        this.body = body;
    }

    public String getAbsPath() {
        return requestLine.getAbsPath();
    }

    public String getParam(String key) {
        return requestLine.getQueryString(key);
    }

    public RequestMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getBody(String key) {
        return body.getBody(key);
    }

    public RequestUri getUri() {
        return requestLine.getUri();
    }

    public HttpVersion getHttpVersion() {
        return requestLine.getHttpVersion();
    }

}
