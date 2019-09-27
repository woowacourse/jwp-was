package webserver;

import java.util.Objects;

public class RequestMapping {
    private final HttpMethod httpMethod;
    private final String uri;

    public RequestMapping(HttpMethod httpMethod, String uri) {
        this.httpMethod = httpMethod;
        this.uri = uri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMapping that = (RequestMapping) o;
        return httpMethod == that.httpMethod &&
                Objects.equals(uri, that.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, uri);
    }
}
