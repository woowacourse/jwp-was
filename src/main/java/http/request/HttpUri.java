package http.request;

import http.common.UriExtension;

import java.net.URI;

public class HttpUri {

    private URI uri;

    public HttpUri(final URI uri) {
        this.uri = uri;
    }

    public String getPath() {
        return this.uri.getPath();
    }

    public String getQuery() {
        return this.uri.getQuery();
    }

    public String findPathPrefix() {
        return UriExtension.of(uri.getPath()).getPathPrefix();
    }
}
