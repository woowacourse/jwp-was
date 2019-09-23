package webserver;

import java.util.Arrays;
import java.util.Optional;

public enum HttpMethod {
    GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE");

    private final String text;

    HttpMethod(String text) {
        this.text = text;
    }

    public static Optional<HttpMethod> from(String text) {
        return Arrays.stream(values())
                .filter(method -> method.text.equals(text))
                .findFirst();
    }
}
