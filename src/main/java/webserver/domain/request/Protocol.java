package webserver.domain.request;

import java.util.Arrays;

import webserver.exception.IncorrectProtocolException;

public enum Protocol {
    ZERO_POINT_NINE(""),
    ONE_POINT_ZERO("HTTP/1.0"),
    ONE_POINT_ONE("HTTP/1.1"),
    TWO_HTTPS("h2"),
    TWO_HTTP("h2c"),
    THREE("h3-Q050");

    private final String identifier;

    Protocol(String identifier) {
        this.identifier = identifier;
    }

    public static Protocol of(String identifier) {
        return Arrays.stream(values())
            .filter(value -> value.identifier.equals(identifier))
            .findAny()
            .orElseThrow(() -> new IncorrectProtocolException(identifier));
    }
}
