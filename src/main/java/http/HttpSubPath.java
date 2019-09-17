package http;

import java.util.Optional;

public class HttpSubPath {
    private final String path;

    public Optional<HttpSubPath> of(String path) {
        return path.contains("/") ? Optional.of(new HttpSubPath(path)) : Optional.empty();
    }

    private HttpSubPath(String path) {
        this.path = path;
    }

    public String get() {
        return this.path;
    }
}