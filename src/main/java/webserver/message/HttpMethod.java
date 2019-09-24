package webserver.message;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private final String method;

    HttpMethod(final String method) {
        this.method = method;
    }

    public boolean matchesMethod(String method) {
        return this.method.equals(method);
    }
}
