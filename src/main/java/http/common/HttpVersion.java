package http.common;

import java.util.Arrays;

public enum HttpVersion {
    HTTP_0_9("HTTP/0.9"),
    HTTP_1_0("HTTP/1.0"),
    HTTP_1_1("HTTP/1.1"),
    HTTP_2_0("HTTP/2.0"),
    HTTP_3_0("HTTP/3.0"),
    NOT_SUPPORTED_VERSION("NOT_SUPPORTED_VERSION");

    private static final HttpVersion DEFAULT_HTTP_VERSION = HTTP_1_1;
    private final String version;

    HttpVersion(String version) {
        this.version = version;
    }

    public static HttpVersion of(String version) {
        return Arrays.stream(values())
                .filter(value -> value.version.equals(version))
                .findAny()
                .orElse(NOT_SUPPORTED_VERSION)
                //.orElseThrow(HttpVersionNotFoundException::new)
                ;
    }

    public String getVersion() {
        return version;
    }

    public String serialize() {
        return this.isNotSupportedVersion() ? DEFAULT_HTTP_VERSION.version : version;
    }

    public boolean isNotSupportedVersion() {
        return NOT_SUPPORTED_VERSION.equals(this);
    }
}