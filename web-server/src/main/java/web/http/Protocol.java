package web.http;

import java.util.Arrays;

public enum Protocol {
    HTTP_1_1("HTTP/1.1"),
    HTTP_1_2("HTTP/1.2");

    private final String protocol;

    Protocol(String protocol) {
        this.protocol = protocol;
    }

    public static Protocol from(String protocol) {
        return Arrays.stream(Protocol.values())
            .filter(x -> isSame(x, protocol))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("잘못된 프로토콜입니다."));
    }

    public static boolean isSame(Protocol protocol, String compare) {
        return protocol.protocol.equals(compare);
    }

    public static Protocol getDefaultProtocol() {
        return HTTP_1_1;
    }

    public String getProtocol() {
        return protocol;
    }
}
