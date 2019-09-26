package controller.exception;

import http.HttpStatus;
import webserver.exception.AbstractHttpException;

public class HttpMethodNotAllowedException extends AbstractHttpException {
    private static final String METHOD_NOT_ALLOWED_EXCEPTION_MESSAGE = "해당하는 메소드를 지원하지 않습니다.";

    public HttpMethodNotAllowedException() {
        super(HttpStatus.METHOD_NOT_ALLOWED, METHOD_NOT_ALLOWED_EXCEPTION_MESSAGE);
    }
}