package webserver;

import java.util.Arrays;

import exception.UnsupportedMethodTypeException;

public enum MethodType {
    GET, HEAD, POST, PUT, PATCH, DELETE, CONNECT, OPTIONS, TRACE;

    public static MethodType of(String input) {
        return Arrays.stream(MethodType.values())
            .filter(value -> value.name().equalsIgnoreCase(input))
            .findFirst()
            .orElseThrow(() -> new UnsupportedMethodTypeException(input));
    }
}
