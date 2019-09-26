package webserver.domain;

import java.util.HashMap;
import java.util.Map;

public enum HttpVersion {
    _1_1("HTTP/1.1");

    private static final Map<String, HttpVersion> PROTOCOL_INDEX = new HashMap<>(values().length + 1, 1f);
    static {
        for (final HttpVersion item : values()) {
            PROTOCOL_INDEX.put(item.protocol, item);
        }
    }

    private final String protocol;

    HttpVersion(final String protocol) {
        this.protocol = protocol;
    }

    public static HttpVersion of(final String string) {
        return PROTOCOL_INDEX.get(string.toUpperCase());
    }

    @Override
    public String toString() {
        return protocol;
    }
}
