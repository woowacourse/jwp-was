package http.model.common;

import http.exceptions.IllegalHttpRequestException;

import java.util.Arrays;

public enum HttpProtocols {
    HTTP1("HTTP/1.1"),
    HTTP2("HTTP/2.0");

    private String protocol;

    HttpProtocols(String protocol) {
        this.protocol = protocol;
    }

    public static HttpProtocols of(String protocol) {
        return Arrays.stream(HttpProtocols.values())
                .filter(value -> value.getProtocol().equals(protocol))
                .findAny()
                .orElseThrow(IllegalHttpRequestException::new);

    }

    public String getProtocol() {
        return this.protocol;
    }
}
