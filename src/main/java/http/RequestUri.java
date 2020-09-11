package http;

import controller.ControllerMapper;

import java.util.Map;
import java.util.Objects;

public class RequestUri {
    private final HttpMethod method;
    private final String url;
    private final Map<String, String> params;

    public RequestUri(HttpMethod method, String url, Map<String, String> params) {
        this.method = method;
        this.url = url;
        this.params = params;
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

    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RequestUri that = (RequestUri) o;
        return getMethod() == that.getMethod() &&
                Objects.equals(getUrl(), that.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMethod(), getUrl());
    }
}
