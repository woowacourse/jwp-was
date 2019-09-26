package webserver.exception;

import http.HttpStatus;

public abstract class AbstractHttpException extends RuntimeException {
    private HttpStatus status;

    public AbstractHttpException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
