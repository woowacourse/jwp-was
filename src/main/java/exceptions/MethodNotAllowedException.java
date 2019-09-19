package exceptions;

import webserver.response.HttpStatus;

public class MethodNotAllowedException extends ErrorResponseException {
    public MethodNotAllowedException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
