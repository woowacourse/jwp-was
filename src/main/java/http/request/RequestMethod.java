package http.request;

import exception.NoMatchHttpMethodException;

import java.util.Arrays;

public enum RequestMethod {
    GET(),
    POST(),
    PUT(),
    DELETE();
    
    public static RequestMethod from(String requestMethod) {
        return Arrays.stream(values())
                .filter(method -> method.name().equals(requestMethod))
                .findAny()
                .orElseThrow(NoMatchHttpMethodException::new);
    }
}
