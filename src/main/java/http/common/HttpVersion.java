package http.common;

import http.exception.InvalidHeaderException;

public enum HttpVersion {
    HTTP_0_9("HTTP/0.9"),
    HTTP_1_0("HTTP/1.0"),
    HTTP_1_1("HTTP/1.1"),
    HTTP_2_0("HTTP/2.0");

    private String version;

    HttpVersion(String version) {
        this.version = version;
    }

    public static boolean isExistVersion(String version) {
        for (HttpVersion value : HttpVersion.values()) {
            if (value.version.equals(version)) {
                return true;
            }
        }
        return false;
    }

    public static HttpVersion getVersion(String version) {
        for (HttpVersion value : HttpVersion.values()) {
            if (value.version.equals(version)) {
                return value;
            }
        }
        throw new InvalidHeaderException("요청한 버전을 찾을 수 없습니다.");
    }
}
