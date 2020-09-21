package webserver;

import exception.NotFoundHttpVersionException;
import java.util.Arrays;

public enum HttpVersion {
    HTTP09("HTTP/0.9"),
    HTTP10("HTTP/1.0"),
    HTTP11("HTTP/1.1"),
    HTTP2("HTTP/2");

    private final String httpVersion;

    HttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public static HttpVersion find(String httpVersion) {
        return Arrays.stream(HttpVersion.values())
            .filter(version -> version.isSameVersion(httpVersion))
            .findFirst()
            .orElseThrow(() -> new NotFoundHttpVersionException(httpVersion + "에 해당하는 HttpVersion을 찾지 못했습니다!"));
    }

    private boolean isSameVersion(String version) {
        return this.httpVersion.equals(version);
    }
}
