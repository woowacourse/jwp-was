package http;

import java.util.Arrays;

import http.excption.NotSupportedHttpProtocolException;

public enum HttpProtocol {
    HTTP_1_0("HTTP/1.0"),
    HTTP_1_1("HTTP/1.1"),
    HTTP_2("HTTP/2");

    private String protocol;

    HttpProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }

    public static HttpProtocol of(String protocol) {
        return Arrays.stream(values())
                .filter(httpProtocol -> httpProtocol.contains(protocol))
                .findFirst()
                .orElseThrow(() -> new NotSupportedHttpProtocolException("지원하지 않는 프로토콜 입니다."));
    }

    private boolean contains(String protocol) {
        return this.protocol.contains(protocol);
    }

    @Override
    public String toString() {
        return "HttpProtocol{" +
                "protocol='" + protocol + '\'' +
                '}';
    }
}
