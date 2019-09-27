package webserver;

import java.util.Arrays;
import java.util.Optional;

public enum HttpMethod {
    GET("doGet"),
    POST("doPost"),
    PUT("doPut"),
    DELETE("doDelete");

    private String handleMethodName;

    HttpMethod(String handleMethodName) {
        this.handleMethodName = handleMethodName;
    }

    public static Optional<HttpMethod> from(String text) {
        return Arrays.stream(values())
                .filter(method -> method.name().equals(text))
                .findFirst();
    }

    public static Optional<HttpMethod> fromHandleMethodName(String text) {
        return Arrays.stream(values()).filter(method -> method.handleMethodName.equals(text))
                .findFirst();
    }
}
