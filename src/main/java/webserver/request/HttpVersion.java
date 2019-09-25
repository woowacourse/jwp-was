package webserver.request;

import java.util.Arrays;

public enum HttpVersion {
    HTTP_1_1("HTTP/1.1"),
    HTTP_2("HTTP/2"),
    DEFAULT("");

    private String version;

    HttpVersion(String version) {
        this.version = version;
    }

    public static HttpVersion of(String version) {
        return Arrays.stream(HttpVersion.values())
                .filter(v -> v.isSameVersion(version))
                .findAny()
                .orElse(DEFAULT);
    }

    private boolean isSameVersion(String version) {
        return this.version.equals(version);
    }

    public String getVersion() {
        return version;
    }
}
