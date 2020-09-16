package webserver;

import exception.NotFoundHttpMethodException;
import java.util.Arrays;

public enum HttpMethod {

    GET("GET", true),
    POST("POST", true),
    PUT("PUT", false),
    DELETE("DELETE", false),
    HEAD("HEAD", false),
    TRACE("TRACE", false),
    CONNECT("CONNECT", false),
    OPTIONS("OPTIONS", false);

    private final String httpMethod;
    private final boolean support;

    HttpMethod(String httpMethod, boolean support) {
        this.httpMethod = httpMethod;
        this.support = support;
    }

    private static HttpMethod find(String httpMethod) {
        return Arrays.stream(HttpMethod.values())
            .filter(method -> method.httpMethod.equals(httpMethod))
            .findFirst()
            .orElseThrow(() -> new NotFoundHttpMethodException(httpMethod + "에 해당하는 HttpMethod를 찾지 못했습니다!"));
    }

    public static boolean isSupported(String httpMethod) {
        HttpMethod foundHttpMethod = find(httpMethod);
        return foundHttpMethod.support;
    }
}
