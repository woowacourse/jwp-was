package webserver.domain.request;

import java.util.Arrays;

public enum Method {
    GET,
    POST,
    PUT,
    DELETE,
    ELSE;

    private final String methodName;

    Method() {
        methodName = name();
    }

    public static Method of(String methodName) {
        return Arrays.stream(values())
            .filter(method -> method.methodName.equals(methodName))
            .findAny()
            .orElse(ELSE);
    }

    public String getMethodName() {
        return methodName;
    }
}
