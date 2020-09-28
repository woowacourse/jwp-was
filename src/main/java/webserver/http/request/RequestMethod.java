package webserver.http.request;

import java.util.Arrays;

public enum RequestMethod {
    GET,
    POST;

    public static RequestMethod of(String methodName) {
        return Arrays.stream(values())
            .filter(requestMethod -> requestMethod.name().equalsIgnoreCase(methodName))
            .findAny()
            .orElse(null);
    }

    public boolean allowBody() {
        return this == POST;
    }
}
