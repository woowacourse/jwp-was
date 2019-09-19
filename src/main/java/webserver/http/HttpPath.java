package http;

import java.net.URLDecoder;
import java.util.Objects;
import java.util.Optional;

public class HttpPath {
    private final String path;

    public static Optional<HttpPath> of(String path) {
        return path.contains("/") ? Optional.of(new HttpPath(path)) : Optional.empty();
    }

    private HttpPath(String path) {
        this.path = path.split("\\?")[0];
    }

    public String get() {
        return this.path;
    }

    public String decoded() {
        return URLDecoder.decode(this.path);
    }

    public String extension() {
        return (this.path.contains(".")) ? this.path.substring(this.path.lastIndexOf(".") + 1) : "";
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

    @Override
    public String toString() {
        return "HttpPath{" +
                "path='" + this.path + '\'' +
                '}';
    }
}