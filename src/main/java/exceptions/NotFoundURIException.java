package exceptions;

import webserver.response.HttpStatus;

public class NotFoundURIException extends RuntimeException {
    public NotFoundURIException(String uri) {
        super("없는 경로 입니다: " + uri);
    }
}
