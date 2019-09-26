package webserver.http;

public enum HttpMethod {
    GET("GET"),
    POST("POST");

    String methodName;

    HttpMethod(String methodName) {
        this.methodName = methodName;
    }
}
