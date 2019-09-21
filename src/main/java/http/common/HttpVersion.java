package http.common;

import exception.InvalidHttpVersionException;

import java.util.Arrays;

public enum HttpVersion {
    HTTP_1_0("HTTP/1.0"), HTTP_1_1("HTTP/1.1"), HTTP_2_0("HTTP/2.0");

    private String version;

    HttpVersion(final String version) {
        this.version = version;
    }

    public static HttpVersion of(String version) {
        return Arrays.stream(HttpVersion.values()).filter(httpVersion -> httpVersion.version.equals(version))
            .findFirst()
            .orElseThrow(InvalidHttpVersionException::new);
    }
}
