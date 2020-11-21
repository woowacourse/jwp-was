package webserver.http;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum HttpVersion {
    HTTP_1_1("HTTP/1.1");

    private final String version;

    HttpVersion(String version) {
        this.version = version;
    }

    public static HttpVersion of(String version) {
        return Arrays.stream(values())
                .filter(hv -> hv.version.equals(version))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("해당하는 HTTP Version 이 없습니다. : " + version)
                );
    }

    public String getVersion() {
        return version;
    }
}
