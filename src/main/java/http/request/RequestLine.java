package http.request;

import controller.ControllerMapper;

import java.util.Objects;

public class RequestLine {
    private final HttpMethod method;
    private final String path;

    public RequestLine(HttpMethod method, String path) {
        this.method = method;
        this.path = path;
    }

    public boolean isEqualRequestType(ControllerMapper url) {
        return this.method.equals(url.getHttpMethod()) && this.path.equals(url.getUrl());
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RequestLine that = (RequestLine) o;
        return getMethod() == that.getMethod() &&
                Objects.equals(getPath(), that.getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMethod(), getPath());
    }
}
