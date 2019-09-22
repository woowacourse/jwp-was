package http.request;

import http.HttpMethod;
import http.HttpVersion;

import java.util.Objects;

public class HttpRequestLine {
    private HttpMethod method;
    private String uri;
//    private String httpVersion;
    private HttpVersion httpVersion;

    public HttpRequestLine(HttpMethod method, String uri, HttpVersion httpVersion) {
        this.method = method;
        this.uri = uri;
        this.httpVersion = httpVersion;
    }

    public HttpRequestLine(String line) {
        String[] requestLine = line.split(" ");
        this.method = HttpMethod.of(requestLine[0]);
        this.uri = requestLine[1];
//        this.httpVersion = requestLine[2];
        this.httpVersion = HttpVersion.of(requestLine[2]);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequestLine that = (HttpRequestLine) o;
        return method == that.method &&
                uri.equals(that.uri) &&
                httpVersion == that.httpVersion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, uri, httpVersion);
    }

    @Override
    public String toString() {
        return method.toString() + " " + uri + " " + httpVersion + "\r\n";
    }
}
