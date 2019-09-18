package http;

import java.util.Objects;

public class RequestMapping {
    private final HttpMethod method;
    private final HttpUri uri;

    public RequestMapping(HttpMethod method, HttpUri uri) {
        this.method = method;
        this.uri = uri;
    }

    public boolean match(HttpMethod method, HttpUri uri) {
        return false;
    }

    public boolean match(RequestMapping mapping) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMapping that = (RequestMapping) o;
        return method == that.method &&
                Objects.equals(uri, that.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, uri);
    }
}
