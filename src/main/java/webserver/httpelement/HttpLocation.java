package webserver.httpelement;

import java.util.Objects;

public final class HttpLocation implements HttpHeaderField {
    private final String url;

    public HttpLocation(String url) {
        this.url = url.trim();
    }

    @Override
    public String toString() {
        return this.url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HttpLocation)) {
            return false;
        }
        final HttpLocation rhs = (HttpLocation) o;
        return Objects.equals(this.url, rhs.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.url);
    }
}