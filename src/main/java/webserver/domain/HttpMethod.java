package webserver.domain;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE,
    OPTIONS,
    HEAD,
    TRACE,
    CONNECT;

    public static HttpMethod of(final String string) {
        return valueOf(string.toUpperCase());
    }
}
