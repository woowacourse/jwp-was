package http.exception;

import http.HttpStatus;
import webserver.exception.AbstractHttpException;

public class InvalidCookieAddException extends AbstractHttpException {
    private static final String INVALID_COOKIE_ADD_EXCEPTION = "잘못된 쿠키 입력입니다.";

    public InvalidCookieAddException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, INVALID_COOKIE_ADD_EXCEPTION);
    }
}
