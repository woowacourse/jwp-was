package exception;

import http.request.RequestMethod;
import http.response.Status;

public class HttpRequestMethodNotSupportedException extends RuntimeException {
    public HttpRequestMethodNotSupportedException(RequestMethod requestMethod) {
        super(String.format(
                "%s%sRequest method %s not supported", Status.METHOD_NOT_ALLOWED, System.lineSeparator(), requestMethod
        ));
    }
}
