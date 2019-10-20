package controller.exception;

import webserver.common.HttpStatus;

public class HttpRequestException extends RuntimeException {
    private static final String BLANK = " ";

    private final String code;

    public HttpRequestException(HttpStatus httpStatus) {
        super(httpStatus.getCode() + BLANK + httpStatus.getMessage());
        this.code = httpStatus.getCode();
    }

    public String getCode() {
        return code;
    }
}
