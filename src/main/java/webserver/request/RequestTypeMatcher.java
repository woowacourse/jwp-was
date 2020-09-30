package webserver.request;

import java.util.Objects;
import webserver.HttpMethod;

public class RequestTypeMatcher {

    private HttpMethod method;
    private String path;

    private RequestTypeMatcher(HttpMethod method, String path) {
        this.method = method;
        this.path = path;
    }

    public static RequestTypeMatcher of(HttpMethod method, String path) {
        return new RequestTypeMatcher(method, path);
    }

    public static RequestTypeMatcher of(RequestType requestType) {
        if (requestType.isGet()) {
            return new RequestTypeMatcher(requestType.getMethod(), "");
        }
        return new RequestTypeMatcher(requestType.getMethod(), requestType.getPath());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestTypeMatcher that = (RequestTypeMatcher) o;
        return method == that.method && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path);
    }
}
