package network;

import network.exception.IllegalHttpVersionException;

import java.util.Arrays;

public enum HttpVersion {
    HTTP_1_0("HTTP/1.0"),
    HTTP_1_1("HTTP/1.1"),
    HTTP_2_0("HTTP/2.0");

    private final String value;

    HttpVersion(final String value) {
        this.value = value;
    }

    public static HttpVersion of(final String name) {
        return Arrays.stream(HttpVersion.values())
                .filter(version -> version.value.equals(name))
                .findAny()
                .orElseThrow(IllegalHttpVersionException::new);
    }
}
