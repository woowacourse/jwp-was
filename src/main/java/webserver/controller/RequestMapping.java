package webserver.controller;

import webserver.request.Method;

import java.util.Objects;

import static webserver.request.Method.GET;
import static webserver.request.Method.POST;

public class RequestMapping {
    private Method method;
    private String uri;

    public RequestMapping(Method method, String uri) {
        this.method = method;
        this.uri = uri;
    }

    public static RequestMapping getMapping(String uri) {
        return new RequestMapping(GET, uri);
    }

    public static RequestMapping postMapping(String uri) {
        return new RequestMapping(POST, uri);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMapping that = (RequestMapping) o;
        return Objects.equals(method, that.method) &&
                Objects.equals(uri, that.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, uri);
    }

    @Override
    public String toString() {
        return "RequestMapping{" +
                "method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }

    public boolean isSameUri(RequestMapping requestMapping) {
        return this.uri.equals(requestMapping.uri);
    }
}
