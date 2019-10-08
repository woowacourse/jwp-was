package webserver.httpelement;

import java.util.Optional;
import java.util.stream.Stream;

public enum HttpMethod {
    GET,
    HEAD,
    POST,
    PUT,
    DELETE,
    CONNECT,
    OPTIONS,
    TRACE,
    PATCH;

    public static Optional<HttpMethod> of(String name) {
        return Stream.of(values())
                    .filter(x -> x.name().equalsIgnoreCase(name.trim()))
                    .findAny();
    }

    @Override
    public String toString() {
        return name();
    }
}