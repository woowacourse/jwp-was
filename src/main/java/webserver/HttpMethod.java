package webserver;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE,
    PATCH,
    HEAD,
    CONNECT,
    OPTIONS,
    TRACE;

    public boolean isSame(String httpMethod) {
        return this.name().equals(httpMethod);
    }
}
