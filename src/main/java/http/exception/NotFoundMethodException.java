package http.exception;

import http.HttpStatus;
import webserver.exception.AbstractHttpException;

public class NotFoundMethodException extends AbstractHttpException {
    private static final String NOT_FOUND_METHOD_EXCEPTION_MESSAGE = "Http Method 를 찾을 수 없습니다.";

    public NotFoundMethodException() {
        super(HttpStatus.METHOD_NOT_ALLOWED, NOT_FOUND_METHOD_EXCEPTION_MESSAGE);
    }
}