package webserver.http.request;

import java.util.Objects;

public class RequestMapping {
    private final String uri;
    private final HttpMethod method;

    public RequestMapping(String uri, HttpMethod method) {
        this.uri = uri;
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RequestMapping that = (RequestMapping)o;
        return Objects.equals(uri, that.uri) &&
                method == that.method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, method);
    }
}
