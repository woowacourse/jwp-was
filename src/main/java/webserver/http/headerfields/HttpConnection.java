package webserver.http.headerfields;

import java.util.Optional;
import java.util.stream.Stream;

public enum HttpConnection implements HttpHeaderField {
    CLOSE("close"),
    KEEP_ALIVE("keep-alive"),
    UPGRADE("upgrade");

    private final String type;

    HttpConnection(String type) {
        this.type = type;
    }

    public static Optional<HttpConnection> of(String name) {
        return Stream.of(values())
                    .filter(x -> x.type.equals(name))
                    .findAny();
    }

    @Override
    public String nameOfField() {
        return "Connection";
    }

    @Override
    public String toString() {
        return this.type;
    }
}