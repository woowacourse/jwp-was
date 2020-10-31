package web.request;

import java.util.Objects;

public class RequestMapping {
    private final String uri;
    private final HttpMethod httpMethod;

    public RequestMapping(final String uri, final HttpMethod httpMethod) {
        this.uri = uri;
        this.httpMethod = httpMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMapping that = (RequestMapping) o;
        return Objects.equals(uri, that.uri) &&
                httpMethod == that.httpMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, httpMethod);
    }
}
