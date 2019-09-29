package webserver.http.request;

public enum RequestMethod {
    GET("get"),
    POST("post");

    private final String name;

    RequestMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
