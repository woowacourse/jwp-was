package http.supoort;

import http.model.HttpMethod;
import http.model.HttpRequest;
import http.model.ServletRequest;

import java.util.Objects;

public class RequestMapping {
    private final HttpMethod method;
    private final String location;

    public RequestMapping(HttpMethod method, String location) {
        this.method = method;
        this.location = location;
    }

    public static RequestMapping GET(String regex) {
        return new RequestMapping(HttpMethod.GET, regex);
    }

    public static RequestMapping POST(String regex) {
        return new RequestMapping(HttpMethod.POST, regex);
    }

    public boolean match(ServletRequest request) {
        return request.getMethod() == method
                && request.getUri().getResourceLocation().equals(location);
    }

    public boolean match(HttpRequest request) {
        return request.getHttpMethod() == method
                && request.getUri().getResourceLocation().equals(location);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestMapping)) return false;
        RequestMapping that = (RequestMapping) o;
        return method == that.method &&
                Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, location);
    }
}
