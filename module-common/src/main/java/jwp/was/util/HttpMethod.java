package jwp.was.util;

import java.util.Arrays;
import jwp.was.exception.NotExistsHttpMethod;

public enum HttpMethod {
    GET(false),
    POST(true),
    PUT(true),
    DELETE(true),
    PATCH(true),
    HEAD(false),
    CONNECT(false),
    OPTIONS(false),
    TRACE(false);

    private final boolean hasRequestBody;

    HttpMethod(boolean hasRequestBody) {
        this.hasRequestBody = hasRequestBody;
    }

    public static HttpMethod from(String httpMethod) {
        return Arrays.stream(HttpMethod.values())
            .filter(method -> method.name().equals(httpMethod))
            .findFirst()
            .orElseThrow(() -> new NotExistsHttpMethod("올바르지 않은 메서드입니다."));
    }

    public boolean isSame(String httpMethod) {
        return this.name().equals(httpMethod);
    }

    public boolean hasRequestBody() {
        return hasRequestBody;
    }
}
