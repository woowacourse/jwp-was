package webserver.http.headerfields;

import java.util.Optional;
import java.util.stream.Stream;

public enum HttpConnection implements HttpHeaderField {
    KEEP_ALIVE,
    CLOSE,
    UPGRADE;

    public static Optional<HttpConnection> of(String name) {
        return Stream.of(values())
                    .filter(x -> x.name().replaceAll("_", "-").equalsIgnoreCase(name))
                    .findAny();
    }

    @Override
    public String toString() {
        return name().replaceAll("_", "-").toLowerCase();
    }
}