package webserver.http.headerfields;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

public class HttpPath {
    private static final Logger logger = LoggerFactory.getLogger(HttpPath.class);
    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    private final String path;

    public static Optional<HttpPath> of(String path) {
        try {
            path = (path.contains("?") && path.lastIndexOf("?") > path.lastIndexOf("/"))
                    ? URLDecoder.decode(path.substring(0, path.lastIndexOf("?")), UTF_8)
                    : URLDecoder.decode(path, UTF_8);
            return Optional.of(new HttpPath(path));
        } catch (UnsupportedEncodingException e) {
            logger.debug(e.getMessage());
            return Optional.empty();
        }
    }

    private HttpPath(String path) {
        this.path = path;
    }

    public String extension() {
        return (this.path.contains(".")) ? this.path.substring(this.path.lastIndexOf(".") + 1) : "";
    }

    @Override
    public String toString() {
        return this.path;
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
        return this.path.equals(rhs.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.path);
    }
}