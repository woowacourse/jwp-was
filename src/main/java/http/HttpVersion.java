package http;

import http.exception.HttpVersionMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum HttpVersion {
    HTTP_1_0("HTTP/1.0"), HTTP_1_1("HTTP/1.1"), HTTP_2_0("HTTP/2.0");

    private String version;

    private static final Map<String, HttpVersion> mappings = new HashMap<>();

    static {
        for (HttpVersion httpVersion : values()) {
            mappings.put(httpVersion.version, httpVersion);
        }
    }

    HttpVersion(String version) {
        this.version = version;
    }

    public static HttpVersion resolve(String version) {
        HttpVersion httpVersion = mappings.get(version);
        if (Objects.isNull(httpVersion)) {
            throw new HttpVersionMismatchException();
        }
        return httpVersion;
    }

    @Override
    public String toString() {
        return version;
    }
}
