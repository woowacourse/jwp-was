package exceptions;

import webserver.response.HttpStatus;

public class ErrorResponseException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ErrorResponseException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
