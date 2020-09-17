package webserver;

import java.util.Arrays;

public enum HttpVersion {
    UNKNOWN("UNKNOWN"),
    VERSION10("HTTP/1.0"),
    VERSION11("HTTP/1.1"),
    VERSION20("HTTP/2.0"),
    VERSION30("HTTP/3.0");

    private final String httpVersion;

    HttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public static HttpVersion from(String httpVersion) {
        return Arrays.stream(values())
                .filter(value -> value.httpVersion.equals(httpVersion))
                .findAny()
                .orElse(UNKNOWN);
    }
}
