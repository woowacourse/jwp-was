package webserver.request;

import java.util.Arrays;

public enum HttpMethod {

    GET("GET", true, false),
    POST("POST", true, true),
    PUT("PUT", false, true),
    DELETE("DELETE", false, false),
    HEAD("HEAD", false, false),
    TRACE("TRACE", false, false),
    CONNECT("CONNECT", false, false),
    OPTIONS("OPTIONS", false, false),
    NONE("NONE", true, false);

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
            .orElse(HttpMethod.NONE);
    }

    public static boolean hasBody(String httpMethod) {
        HttpMethod foundHttpMethod = find(httpMethod);
        return foundHttpMethod.body;
    }

    public boolean isSame(String httpMethod) {
        return this.httpMethod.equals(httpMethod);
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public boolean isSupport() {
        return support;
    }

    public boolean getBody() {
        return body;
    }
}
