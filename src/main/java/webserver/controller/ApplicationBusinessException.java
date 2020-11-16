package webserver.controller;

import webserver.http.response.StatusCode;

public class ApplicationBusinessException extends RuntimeException {
    public ApplicationBusinessException(String message) {
        super(message);
    }

    public StatusCode getStatus() {
        return StatusCode.BAD_REQUEST;
    }
}
