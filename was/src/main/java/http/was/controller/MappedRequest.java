package http.was.controller;

import java.util.Objects;

import http.was.http.HttpMethod;

public class MappedRequest {

    private HttpMethod method;
    private String path;

    public MappedRequest(HttpMethod method, String path) {
        this.method = method;
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MappedRequest that = (MappedRequest)o;
        return method == that.method &&
                path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path);
    }
}
