package webserver.controller.request.header;

public enum HttpMethod {
    GET("GET",false),
    POST("POST", true),
    PUT("PUT", true),
    DELETE("DELETE", false);

    private final String method;
    private final boolean hasBody;

    HttpMethod(String method, boolean hasBody) {
        this.method = method;
        this.hasBody = hasBody;
    }

    public boolean hasBody() {
        return hasBody;
    }
}
