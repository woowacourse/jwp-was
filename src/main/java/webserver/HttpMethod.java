package webserver;

import exception.NotFoundHttpMethodException;
import java.util.Arrays;

public enum HttpMethod {

    GET("GET", true, false),
    POST("POST", true, true),
    PUT("PUT", false, true),
    DELETE("DELETE", false, false),
    HEAD("HEAD", false, false),
    TRACE("TRACE", false, false),
    CONNECT("CONNECT", false, false),
    OPTIONS("OPTIONS", false, false);

    private final String httpMethod;
    private final boolean support;
    private final boolean body;

    HttpMethod(String httpMethod, boolean support, boolean body) {
        this.httpMethod = httpMethod;
        this.support = support;
        this.body = body;
    }

    public static HttpMethod find(String httpMethod) {
        return Arrays.stream(HttpMethod.values())
            .filter(method -> method.httpMethod.equals(httpMethod))
            .findFirst()
            .orElseThrow(() -> new NotFoundHttpMethodException(httpMethod + "에 해당하는 HttpMethod를 찾지 못했습니다!"));
    }

    public static boolean isSupported(String httpMethod) {
        HttpMethod foundHttpMethod = find(httpMethod);
        return foundHttpMethod.support;
    }

    public static boolean hasBody(String httpMethod) {
        HttpMethod foundHttpMethod = find(httpMethod);
        return foundHttpMethod.body;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public boolean getBody() {
        return body;
    }
}
