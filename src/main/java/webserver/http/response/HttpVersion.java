package webserver.http.response;

import java.util.Arrays;

public enum HttpVersion {
    HTTP1("HTTP/1.1"), HTTP2("HTTP/2.0");

    private final String name;

    HttpVersion(String name) {
        this.name = name;
    }

    public static HttpVersion getHttpVersion(String target) {
        return Arrays.stream(HttpVersion.values())
                .filter(version -> target.equals(version.getName()))
                .findFirst()
                .orElse(HTTP1);
    }

    private String getName() {
        return this.name;
    }
}
