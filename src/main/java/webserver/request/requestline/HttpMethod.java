package webserver.request.requestline;

import exception.IllegalHttpMethodException;

public enum HttpMethod {
    GET, POST, PUT, DELETE;

    public static HttpMethod of(String method) {
        try {
            return HttpMethod.valueOf(method);
        } catch (IllegalArgumentException e) {
            throw new IllegalHttpMethodException();
        }
    }
}
