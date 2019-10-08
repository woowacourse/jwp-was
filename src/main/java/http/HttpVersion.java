package http;

import http.request.NotSupportedHttpVersionException;

import java.util.Arrays;

public enum HttpVersion {

    HTTP_0_9("HTTP/0.9"), HTTP_1_1("HTTP/1.1"), HTTP_2_0("HTTP/2.0");

    private String version;

    HttpVersion(String version) {
        this.version = version;
    }

    public static HttpVersion findVersion(String name) {
        return Arrays.stream(values()).filter(v -> v.getVersion().equals(name))
                .findAny().orElseThrow(NotSupportedHttpVersionException::new);
    }

    public String getVersion() {
        return version;
    }
}
