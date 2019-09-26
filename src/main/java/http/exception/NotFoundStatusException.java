package http.exception;

import http.HttpStatus;
import webserver.exception.AbstractHttpException;

public class NotFoundStatusException extends AbstractHttpException {
    private static final String NOT_FOUND_STATUS_EXCEPTION_MESSAGE = "Http Status 를 찾을 수 없습니다.";

    public NotFoundStatusException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, NOT_FOUND_STATUS_EXCEPTION_MESSAGE);
    }
}