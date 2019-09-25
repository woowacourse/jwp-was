package http;

import java.util.Arrays;

public enum HttpVersion {
    HTTP_1_1("HTTP/1.1");

    private String version;

    HttpVersion(String version) {
        this.version = version;
    }

    public static HttpVersion of(String version) {
        return Arrays.stream(values())
                .filter(value -> value.version.equals(version))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                ;
    }
}
