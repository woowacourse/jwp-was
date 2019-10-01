package dev.luffy.http;

import dev.luffy.http.request.HttpRequestMethod;

import java.util.Objects;

public class RequestMapping {

    private HttpRequestMethod httpRequestMethod;
    private String path;

    public RequestMapping(HttpRequestMethod httpRequestMethod, String path) {
        this.httpRequestMethod = httpRequestMethod;
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMapping that = (RequestMapping) o;
        return httpRequestMethod == that.httpRequestMethod &&
                Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpRequestMethod, path);
    }
}
