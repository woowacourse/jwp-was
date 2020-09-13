package controller;

import webserver.HttpStatusCode;
import webserver.dto.HttpRequest;
import webserver.dto.HttpResponse;

public class ControllerAdvice {

    public HttpResponse handleException(HttpRequest httpRequest, Exception exception) {
        if (exception instanceof IllegalArgumentException
            || exception instanceof NullPointerException) {
            return handleHttpStatusCode(httpRequest, HttpStatusCode.BAD_REQUEST);
        }
        return handleHttpStatusCode(httpRequest, HttpStatusCode.INTERNAL_SERVER_ERROR);
    }

    public HttpResponse handleHttpStatusCode(HttpRequest httpRequest,
        HttpStatusCode httpStatusCode) {
        return HttpResponse.of(
            httpRequest.getProtocol(),
            httpStatusCode,
            httpStatusCode.getMessage()
        );
    }
}
