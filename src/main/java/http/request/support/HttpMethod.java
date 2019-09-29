package http.request.support;

public enum HttpMethod {
    GET("GET"),
    POST("POST");

    private String methodValue;

    HttpMethod(String methodValue) {
        this.methodValue = methodValue;
    }
}
