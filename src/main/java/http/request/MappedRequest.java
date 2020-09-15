package http.request;

import java.util.Objects;

public class MappedRequest {
    private HttpMethod httpMethod;
    private String path;

    public MappedRequest(HttpMethod httpMethod, String path) {
        this.httpMethod = httpMethod;
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MappedRequest that = (MappedRequest)o;
        return httpMethod == that.httpMethod &&
            Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, path);
    }
}
