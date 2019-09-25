package http.request.core;

import http.exception.HttpRequestVersionException;

import java.util.Arrays;

public enum RequestVersion {
    HTTP_VERSION_0_9("HTTP/0.9"),
    HTTP_VERSION_1_0("HTTP/1.0"),
    HTTP_VERSION_1_1("HTTP/1.1"),
    HTTP_VERSION_2_0("HTTP/2.0");

    private final String version;

    RequestVersion(final String version) {
        this.version = version;
    }

    public static RequestVersion of(String requestVersion) {
        return Arrays.stream(values())
                .filter(value -> value.version.equals(requestVersion))
                .findAny()
                .orElseThrow(HttpRequestVersionException::new);
    }

    public String getVersion() {
        return version;
    }
}
