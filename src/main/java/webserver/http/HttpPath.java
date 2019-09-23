package webserver.http;

import java.net.URLDecoder;
import java.util.Objects;

public class HttpPath {
    private final String path;

    public HttpPath(String path) {
        this.path = (path.contains("?") && path.lastIndexOf("?") > path.lastIndexOf("/"))
                ? path.substring(0, path.lastIndexOf("?"))
                : path;
    }

    public String extension() {
        return (this.path.contains(".")) ? this.path.substring(this.path.lastIndexOf(".") + 1) : "";
    }

    @Override
    public String toString() {
        return URLDecoder.decode(this.path);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HttpPath)) {
            return false;
        }
        final HttpPath rhs = (HttpPath) o;
        return Objects.equals(this.path, rhs.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.path);
    }
}