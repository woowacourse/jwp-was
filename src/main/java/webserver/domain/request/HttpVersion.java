package webserver.domain.request;

import java.util.Arrays;

public enum HttpVersion {
    ONE_POINT_ZERO("1.0"),
    ONE_POINT_ONE("1.1"),
    TWO_POINT_ZERO("2.0"),
    UNDER_ONE_POINT_ZERO("");

    private String version;

    HttpVersion(String version) {
        this.version = version;
    }

    public static HttpVersion of(String version) {
        return Arrays.stream(values())
            .filter(httpVersion -> httpVersion.version.equals(version))
            .findAny()
            .orElse(UNDER_ONE_POINT_ZERO);
    }

    public String getVersion() {
        return version;
    }
}
