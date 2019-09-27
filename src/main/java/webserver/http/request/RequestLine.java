package webserver.http.request;

import webserver.http.response.HttpVersion;

public class RequestLine {
    private final RequestMethod method;
    private final RequestUri uri;
    private final HttpVersion httpVersion;

    public RequestLine(String method, String uri, String httpVersion) {
        this.method = RequestMethod.valueOf(method);
        this.uri = new RequestUri(uri);
        this.httpVersion = HttpVersion.getHttpVersion(httpVersion);
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getQueryString(String key) {
        return uri.getQueryString(key);
    }

    public String getAbsPath() {
        return uri.getAbsPath();
    }

    public RequestUri getUri() {
        return uri;
    }
}
