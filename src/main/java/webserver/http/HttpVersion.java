package webserver.http;

import java.util.HashMap;
import java.util.Map;

public enum HttpVersion {
    HTTP_0_9("0.9"),
    HTTP_1_0("1.0"),
    HTTP_1_1("1.1"),
    HTTP_2_0("2.0");

    public static final String HTTP = "HTTP";
    public static final Map<String, HttpVersion> mapping = new HashMap<>();

    static {
        for (final HttpVersion httpVersion : values()) {
            mapping.put(httpVersion.getHttpVersion(), httpVersion);
        }
    }

    private final String protocol;
    private final String version;

    HttpVersion(final String version) {
        this.protocol = HTTP;
        this.version = version;
    }

    public static HttpVersion of(final String version) {
        final HttpVersion httpVersion = mapping.get(version);
        if (httpVersion == null) {
            throw new IllegalArgumentException("UnSupport HttpVersion");
        }
        return httpVersion;
    }

    public String getHttpVersion() {
        return protocol + "/" + version;
    }
}
