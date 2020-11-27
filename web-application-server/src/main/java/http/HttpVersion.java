package http;

import java.util.Arrays;

public enum HttpVersion {
    NONE("NONE"),
    HTTP_09("HTTP/0.9"),
    HTTP_10("HTTP/1.0"),
    HTTP_11("HTTP/1.1");

    private String version;

    public static HttpVersion from(String version) {
        return Arrays.stream(HttpVersion.values())
            .filter(value -> value.version.equals(version))
            .findFirst()
            .orElse(NONE);
    }

    HttpVersion(String version) {
        this.version = version;
    }

    public boolean isValid() {
        return this == HTTP_11;
    }

}
