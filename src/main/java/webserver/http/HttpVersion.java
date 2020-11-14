package webserver.http;

import java.util.Arrays;

public enum HttpVersion {
    HTTP_1_1("HTTP/1.1"),
    HTTP_2("HTTP/2");

    private final String version;

    HttpVersion(String version) {
        this.version = version;
    }

    public static HttpVersion find(String requestVersion) {
        return Arrays.stream(values())
                .filter(httpVersion -> httpVersion.version.equals(requestVersion))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getVersion() {
        return version;
    }
}
