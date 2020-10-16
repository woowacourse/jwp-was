package http;

import java.util.Arrays;

public enum HttpVersion {
    UNKNOWN("UNKNOWN"),
    VERSION_1_0("HTTP/1.0"),
    VERSION_1_1("HTTP/1.1"),
    VERSION_2_0("HTTP/2.0"),
    VERSION_3_0("HTTP/3.0");

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
