package http;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE;

    public static void match(HttpMethod method, HttpRequest httpRequest) {
        if (httpRequest.getMethod() != method) {
            throw new NotSupportedHTTPMethod();
        }
    }
}
