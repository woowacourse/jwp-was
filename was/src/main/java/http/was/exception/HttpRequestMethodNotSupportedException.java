package http.was.exception;

import http.was.http.HttpMethod;
import http.was.http.response.Status;

public class HttpRequestMethodNotSupportedException extends RuntimeException {
    public HttpRequestMethodNotSupportedException(HttpMethod requestMethod) {
        super(String.format(
                "%s%sRequest method %s not supported", Status.METHOD_NOT_ALLOWED, System.lineSeparator(), requestMethod
        ));
    }
}
