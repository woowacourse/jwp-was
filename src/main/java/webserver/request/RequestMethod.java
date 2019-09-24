package webserver.request;

public enum RequestMethod {
    GET("get"),
    POST("post");

    public String method;

    RequestMethod(String method) {
        this.method = method;
    }
}
