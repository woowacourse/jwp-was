package exception;

import http.HttpMethod;
import http.response.Status;

public class HttpRequestMethodNotSupportedException extends RuntimeException {
    public HttpRequestMethodNotSupportedException(HttpMethod requestMethod) {
        super(String.format(
                "%s%sRequest method %s not supported", Status.METHOD_NOT_ALLOWED, System.lineSeparator(), requestMethod
        ));
    }
}
