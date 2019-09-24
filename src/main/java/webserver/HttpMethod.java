package webserver;

import java.util.Arrays;
import java.util.Optional;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE;

    public static Optional<HttpMethod> from(String text) {
        return Arrays.stream(values())
                .filter(method -> method.name().equals(text))
                .findFirst();
    }
}
