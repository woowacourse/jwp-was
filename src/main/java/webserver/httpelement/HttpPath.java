package webserver.httpelement;

import utils.io.FileExtension;

import java.net.URLDecoder;
import java.util.Objects;
import java.util.Optional;

public final class HttpPath {
    private final String path;

    public static Optional<HttpPath> of(String path) {
        final String withoutParams = path.contains("?")
                ? path.substring(0, path.lastIndexOf("?")).trim()
                : path.trim();
        if (withoutParams.charAt(0) != '/' || withoutParams.split("\\s+").length > 1) {
            return Optional.empty();
        }
        return Optional.of(new HttpPath(withoutParams));
    }

    private HttpPath(String path) {
        this.path = path;
    }

    public FileExtension extension() {
        return new FileExtension(this.path);
    }

    public String encoded() {
        return this.path;
    }

    public String decoded() {
        return URLDecoder.decode(this.path);
    }

    @Override
    public String toString() {
        return decoded();
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