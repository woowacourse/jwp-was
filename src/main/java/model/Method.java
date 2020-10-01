package model;

import java.util.Arrays;

public enum Method {
    GET("GET", true),
    HEAD("HEAD", false),
    POST("POST", true);

    private final String method;
    private final boolean isNeedBody;

    Method(String method, boolean isNeedBody) {
        this.method = method;
        this.isNeedBody = isNeedBody;
    }

    public static Method of(String method){
        return Arrays.stream(values())
            .filter(m->m.method.equals(method))
            .findFirst()
            .orElseThrow(()-> new IllegalArgumentException("Not Implemented Method"));
    }

    public boolean isNeedBody() {
        return isNeedBody;
    }
}
