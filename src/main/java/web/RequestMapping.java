package web;

import java.util.Objects;

public class RequestMapping {
    private final String uri;
    private final HttpMethod httpMethod;

    public RequestMapping(final String uri, final HttpMethod httpMethod) {
        this.uri = uri;
        this.httpMethod = httpMethod;
    }

    private String getUri() {
        return uri;
    }

    private HttpMethod getHttpMethod() {
        return httpMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMapping that = (RequestMapping) o;
        return Objects.equals(getUri(), that.getUri()) &&
                getHttpMethod() == that.getHttpMethod();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUri(), getHttpMethod());
    }
}
