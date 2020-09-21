package http;

import java.util.Arrays;

public enum RequestMethod {
    GET,
    POST,
    PUT,
    DELETE;

    public static RequestMethod of(String method) {
        return Arrays.stream(values())
                .filter(t -> t.name().equals(method))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid method"));
    }
}
