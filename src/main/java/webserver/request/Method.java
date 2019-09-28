package webserver.request;

public enum Method {
    GET("get"),
    POST("post"),
    PUT("put"),
    DELETE("delete");

    private String method;

    Method(String method) {
        this.method = method;
    }
}
