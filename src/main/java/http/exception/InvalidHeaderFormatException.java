package http.exception;

import http.HttpStatus;
import webserver.exception.AbstractHttpException;

public class InvalidHeaderFormatException extends AbstractHttpException {
    private static final String INVALID_HEADER_FORMAT_EXCEPTION = "올바르지 않은 헤더 형식입니다.";

    public InvalidHeaderFormatException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, INVALID_HEADER_FORMAT_EXCEPTION);
    }
}
