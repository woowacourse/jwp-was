package http;

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
}
