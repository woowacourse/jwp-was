package web.request;

import exception.InvalidHttpRequestException;

import java.util.Arrays;

public enum MethodType {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private final String name;

    MethodType(String name) {
        this.name = name;
    }

    public static MethodType createMethodByName(String name) {
        return Arrays.stream(MethodType.values())
                .filter(methodType -> methodType.name.equals(name))
                .findAny()
                .orElseThrow(InvalidHttpRequestException::new);
    }

    public boolean isGet() {
        return GET.equals(this);
    }

    public boolean isPost() {
        return POST.equals(this);
    }

    public boolean isPut() {
        return PUT.equals(this);
    }

    public boolean isDelete() {
        return DELETE.equals(this);
    }

    public String getName() {
        return name;
    }
}
