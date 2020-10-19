package webserver;

import java.util.Arrays;

import exception.NotFoundServletException;
import webserver.response.ModelAndView;
import webserver.response.ServletResponse;
import webserver.response.StatusCode;

public enum ExceptionHandler {
    NOT_FOUND(404, ServletResponse.of(StatusCode.NOT_FOUND, ModelAndView.of("static/notFound.html"))),
    SERVER_ERROR(500, ServletResponse.of(StatusCode.INTERNAL_SERVER_ERROR, ModelAndView.of("static/notFound.html")));

    private final int exception;
    private final ServletResponse response;

    ExceptionHandler(int exception, ServletResponse response) {
        this.exception = exception;
        this.response = response;
    }

    public static ExceptionHandler of(int exception) {
        return Arrays.stream(ExceptionHandler.values())
            .filter(ex -> ex.exception == exception)
            .findAny()
            .orElseThrow(() -> new NotFoundServletException(exception));
    }

    public ServletResponse getResponse() {
        return response;
    }

    public int getException() {
        return exception;
    }
}
