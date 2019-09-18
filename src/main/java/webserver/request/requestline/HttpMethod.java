package webserver.request.requestline;

public enum HttpMethod {
    GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE");

    private String method;

    HttpMethod(String method) {
        this.method = method;
    }
}
