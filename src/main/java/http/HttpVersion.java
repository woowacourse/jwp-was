package http;

import http.exception.NotFoundVersionException;

import java.util.Arrays;

public enum HttpVersion {
    V_1_1(1.1, "HTTP/1.1"),
    V_2_0(2.0, "HTTP/2.0");

    private static final String HTTP_VERSION_DELIMITER = "/";
    private Double versionNumber;
    private String httpVersion;

    HttpVersion(double versionNumber, String httpVersion) {
        this.versionNumber = versionNumber;
        this.httpVersion = httpVersion;
    }

    public static HttpVersion of(String version) {
        String[] versionTokens = version.split(HTTP_VERSION_DELIMITER);
        Double versionTokenNumber = Double.parseDouble(versionTokens[1]);
        return Arrays.stream(HttpVersion.values())
                .filter(httpVersionNumber -> httpVersionNumber.versionNumber.equals(versionTokenNumber))
                .findAny()
                .orElseThrow(NotFoundVersionException::new)
                ;
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}
