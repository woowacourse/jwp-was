package http;

import controller.ControllerMapper;

import java.util.Objects;

public class RequestLine {
    private final HttpMethod method;
    private final String url;

    public RequestLine(HttpMethod method, String url) {
        this.method = method;
        this.url = url;
    }

    public boolean isEqualRequestType(ControllerMapper url) {
        return this.method.equals(url.getHttpMethod()) && this.url.equals(url.getUrl());
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RequestLine that = (RequestLine) o;
        return getMethod() == that.getMethod() &&
                Objects.equals(getUrl(), that.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMethod(), getUrl());
    }
}
