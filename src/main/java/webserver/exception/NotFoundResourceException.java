package webserver.exception;

import http.HttpStatus;

public class NotFoundResourceException extends AbstractHttpException {
    private static final String NOT_FOUND_RESOURCE_EXCEPTION = "해당하는 자원을 찾을 수 없습니다.";

    public NotFoundResourceException() {
        super(HttpStatus.NOT_FOUND, NOT_FOUND_RESOURCE_EXCEPTION);
    }
}