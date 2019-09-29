package webserver.http.request;

public enum RequestMethod {
    GET("get"),
    POST("post");

    private final String method;

    RequestMethod(String method) {
        this.method = method;
    }
}
