package controller;

import http.request.HttpMethod;
import http.request.HttpUri;

import java.util.Objects;

public class RequestMapping {

    private HttpMethod httpMethod;
    private HttpUri uri;

    private RequestMapping(final HttpMethod httpMethod, final HttpUri uri) {
        this.httpMethod = httpMethod;
        this.uri = uri;
    }

    public static RequestMapping of(final HttpMethod httpMethod, final HttpUri uri) {
        return new RequestMapping(httpMethod, uri);
    }

    public boolean isEquals(RequestMapping another, String regex) {
        return httpMethod.equals(another.httpMethod) && uri.isMatches(regex);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RequestMapping that = (RequestMapping) o;
        return httpMethod == that.httpMethod &&
            uri.isEqualsPath(that.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, uri);
    }
}
