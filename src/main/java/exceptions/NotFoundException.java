package exceptions;

import webserver.response.HttpStatus;

public class NotFoundException extends ErrorResponseException {
    public NotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
