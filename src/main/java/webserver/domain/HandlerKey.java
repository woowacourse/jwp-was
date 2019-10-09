package webserver.domain;

import java.util.Objects;

public class HandlerKey {
    private String url;
    private HttpMethod method;

    public HandlerKey(final String url, final HttpMethod method) {
        this.url = url;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    @Override
    public boolean equals(final Object another) {
        if (this == another) return true;
        if (another == null || getClass() != another.getClass()) return false;
        final HandlerKey that = (HandlerKey) another;
        return Objects.equals(url, that.url) &&
                method == that.method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, method);
    }
}
