package http;

import java.util.Arrays;

public enum RequestVersion {
    VERISON_0_9("HTTP/0.9"),
    VERSION_1_0("HTTP/1.0"),
    VERSION_1_1("HTTP/1.1"),
    VERSION_2("HTTP/2");

    private String version;

    RequestVersion(String version) {
        this.version = version;
    }

    public static RequestVersion from(String requestVersion) {
        return Arrays.stream(values())
                .filter(version -> version.getVersion().equals(requestVersion))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getVersion() {
        return version;
    }
}
