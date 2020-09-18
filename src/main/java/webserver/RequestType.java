package webserver;

import java.util.Objects;

public class RequestType {

    private HttpMethod method;
    private String path;

    public RequestType(HttpMethod method, String path) {
        this.method = method;
        this.path = path;
    }

    public static RequestType of(HttpMethod method, String path) {
        return new RequestType(method, path);
    }

    public static RequestType of(Request request) {
        if (request.isGet()) {
            return new RequestType(request.getMethod(), "");
        }
        return new RequestType(request.getMethod(), request.getPath());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestType that = (RequestType) o;
        return method == that.method && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path);
    }
}
