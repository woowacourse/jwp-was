package http.request;

import http.HttpMethod;
import http.HttpVersion;

public class HttpRequestLine {
    private HttpMethod method;
    private HttpUri uri;
    private HttpVersion httpVersion;

    public HttpRequestLine(String line) {
        String[] requestLine = line.split(" ");
        this.method = HttpMethod.of(requestLine[0]);
        this.uri = HttpUri.of(requestLine[1]);
        this.httpVersion = HttpVersion.of(requestLine[2]);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return uri.getPath();
    }

    @Override
    public String toString() {
        return method.toString() + " " + uri.getPath() + " " + httpVersion + "\r\n";
    }
}
