package webserver.http;

import webserver.http.exception.HttpRequestVersionException;

import java.util.Arrays;

public enum HttpVersion {
    HTTP_VERSION_0_9("HTTP/0.9"),
    HTTP_VERSION_1_0("HTTP/1.0"),
    HTTP_VERSION_1_1("HTTP/1.1"),
    HTTP_VERSION_2_0("HTTP/2.0");

    private final String version;

    HttpVersion(final String version) {
        this.version = version;
    }

    public static HttpVersion of(String httpVersion) {
        return Arrays.stream(values())
                .filter(value -> value.version.equals(httpVersion))
                .findAny()
                .orElseThrow(HttpRequestVersionException::new);
    }

    public String getVersion() {
        return version;
    }
}
