package http.common;

import http.common.exception.HttpVersionNotFoundException;

import java.util.Arrays;

public enum HttpVersion {
    HTTP_0_9("HTTP/0.9"),
    HTTP_1_0("HTTP/1.0"),
    HTTP_1_1("HTTP/1.1"),
    HTTP_2_0("HTTP/2.0"),
    HTTP_3_0("HTTP/3.0");

    private final String version;

    HttpVersion(String version) {
        this.version = version;
    }

    public static HttpVersion of(String version) {
        return Arrays.stream(values())
                .filter(value -> value.version.equals(version))
                .findAny()
                .orElseThrow(HttpVersionNotFoundException::new)
                ;
    }
}