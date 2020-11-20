package domain.request;

import java.util.Arrays;

public enum RequestMethod {
    GET, POST, PUT, DELETE;

    public static RequestMethod from(String method) {
        String target = method.toUpperCase();
        return Arrays.stream(values())
            .filter(requestMethod -> requestMethod.name().equals(target))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("잘못된 요청메소드입니다. method =" + method));
    }
}
