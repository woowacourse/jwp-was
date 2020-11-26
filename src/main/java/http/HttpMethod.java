package http;

import webserver.exception.NotImplementedException;

public enum HttpMethod {
    GET,
    POST;

    public static HttpMethod from(String method) {
        try {
            return valueOf(method);
        } catch (IllegalArgumentException ignored){
            throw new NotImplementedException();
        }
    }
}
