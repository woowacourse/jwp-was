package http.request;

import http.HttpMethod;

public class HttpRequestLine {
    private HttpMethod method;
    private String uri;
    private String httpVersion;

    public HttpRequestLine(String line) {
        String[] requestLine = line.split(" ");
        this.method = HttpMethod.of(requestLine[0]);
        this.uri = requestLine[1];
        this.httpVersion = requestLine[2];
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return method.toString() + " " + uri + " " + httpVersion + "\r\n";
    }
}
