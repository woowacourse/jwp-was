package http.request;

import http.HttpRequestMethod;
import http.HttpVersion;
import http.MediaType;

import java.util.Objects;

public class HttpRequest {
    private HttpRequestStartLine httpRequestStartLine;
    private HttpRequestHeader httpRequestHeader;
    private HttpRequestBody httpRequestBody;
    private Parameters parameters;

    public HttpRequest(HttpRequestStartLine httpRequestStartLine, HttpRequestHeader httpRequestHeader, HttpRequestBody httpRequestBody) {
        this.httpRequestStartLine = httpRequestStartLine;
        this.httpRequestHeader = httpRequestHeader;
        this.httpRequestBody = httpRequestBody;

        if (httpRequestStartLine.hasBody()) {
            parameters = new Parameters(httpRequestBody.getBody());
            return;
        }
        parameters = new Parameters(httpRequestStartLine.getQueryString());
    }

    public boolean isContainExtension() {
        int lastIndex = httpRequestStartLine.getUri().lastIndexOf("/");
        String extension = httpRequestStartLine.getUri().substring(lastIndex + 1);
        return MediaType.isContain(extension);
    }

    public String getParameter(String key) {
        return parameters.getParameter(key);
    }

    public HttpRequestMethod getMethod() {
        return httpRequestStartLine.getHttpRequestMethod();
    }

    public String getPath() {
        return httpRequestStartLine.getPath();
    }

    public String getUri() {
        return httpRequestStartLine.getUri();
    }

    public HttpVersion getHttpVersion() {
        return httpRequestStartLine.getHttpVersion();
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "httpRequestStartLine=" + httpRequestStartLine +
                ", httpRequestHeader=" + httpRequestHeader +
                ", httpRequestBody=" + httpRequestBody +
                ", parameters=" + parameters +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest that = (HttpRequest) o;
        return httpRequestStartLine.equals(that.httpRequestStartLine) &&
                httpRequestHeader.equals(that.httpRequestHeader) &&
                httpRequestBody.equals(that.httpRequestBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpRequestStartLine, httpRequestHeader, httpRequestBody);
    }
}
