package http;

import java.util.Arrays;

import exceptions.InvalidHttpRequestException;

public enum HttpVersion {

    HTTP1_1("HTTP/1.1"),
    HTTP2_0("HTTP/2.0");

    private String version;

    HttpVersion(String version) {
        this.version = version;
    }

    public static HttpVersion from(String version) {
        return Arrays.stream(values())
            .filter(value -> value.version.equals(version))
            .findFirst()
            .orElseThrow(InvalidHttpRequestException::new);
    }

    public String getVersion() {
        return version;
    }
}
