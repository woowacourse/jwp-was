package http.request;

import java.util.Objects;

import http.HttpMethod;

public class HttpRequestMapping {

    private final HttpMethod httpMethod;
    private final String requestPath;

    private HttpRequestMapping(HttpMethod httpMethod, String requestPath) {
        this.httpMethod = httpMethod;
        this.requestPath = requestPath;
    }

    public static HttpRequestMapping GET(String requestPath) {
        return new HttpRequestMapping(HttpMethod.GET, requestPath);
    }

    public static HttpRequestMapping POST(String requestPath) {
        return new HttpRequestMapping(HttpMethod.POST, requestPath);
    }

    public boolean match(HttpRequest httpRequest) {
        return httpRequest.getRequestLine().getMethod() == httpMethod
            && httpRequest.getRequestLine().getPath().equals(requestPath);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof HttpRequestMapping))
            return false;
        HttpRequestMapping that = (HttpRequestMapping)o;
        return httpMethod == that.httpMethod &&
            Objects.equals(requestPath, that.requestPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, requestPath);
    }
}
