package http;

import java.util.Objects;

public class RequestUri {
    private HttpMethod method;
    private String uri;

    public RequestUri(String line) {
        this.method = HttpMethod.valueOf(line.split(" ")[0]);
        this.uri = line.split(" ")[1];
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RequestUri that = (RequestUri) o;
        return getMethod() == that.getMethod() &&
                Objects.equals(getUri(), that.getUri());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMethod(), getUri());
    }
}
