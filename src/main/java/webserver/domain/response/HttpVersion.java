package webserver.domain.response;

import java.util.Arrays;

public enum HttpVersion {
    HTTP_0_9("HTTP/0.9"),
    HTTP_1_0("HTTP/1.0"),
    HTTP_1_1("HTTP/1.1");

    private final String version;

    HttpVersion(final String version) {
        this.version = version;
    }

    public static HttpVersion of(String httpVersion) {
        return Arrays.stream(values())
                .filter(v -> v.version.equals(httpVersion))
                .findFirst()
                .orElse(HttpVersion.HTTP_1_1);
    }

    public String getVersion() {
        return version;
    }
}
