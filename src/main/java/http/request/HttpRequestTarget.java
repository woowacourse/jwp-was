package http.request;

import http.QueryString;

import java.util.Objects;

public class HttpRequestTarget {
    private Resource resource;
    private QueryString queryString;

    public HttpRequestTarget(Resource resource, QueryString queryString) {
        this.resource = resource;
        this.queryString = queryString;
    }

    public String getQueryString() {
        return queryString.getQueryString();
    }

    public String getUri() {
        return resource.getUri();
    }

    @Override
    public String toString() {
        return "HttpRequestTarget{" +
                "resource=" + resource +
                ", queryString=" + queryString +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequestTarget that = (HttpRequestTarget) o;
        return resource.equals(that.resource) &&
                queryString.equals(that.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resource, queryString);
    }
}
