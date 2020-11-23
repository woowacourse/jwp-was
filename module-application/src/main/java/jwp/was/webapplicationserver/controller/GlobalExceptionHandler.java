package jwp.was.webapplicationserver.controller;

import jwp.was.dto.HttpRequest;
import jwp.was.dto.HttpResponse;
import jwp.was.util.HttpStatusCode;

public class GlobalExceptionHandler {

    public HttpResponse handleCauseException(HttpRequest httpRequest, Exception exception) {
        if (exception.getCause() instanceof IllegalArgumentException
            || exception.getCause() instanceof NullPointerException) {
            return handleHttpStatusCode(httpRequest, HttpStatusCode.BAD_REQUEST);
        }
        return handleHttpStatusCode(httpRequest, HttpStatusCode.INTERNAL_SERVER_ERROR);
    }

    public HttpResponse handleHttpStatusCode(HttpRequest httpRequest,
        HttpStatusCode httpStatusCode) {
        return HttpResponse.of(
            httpRequest.getHttpVersion(),
            httpStatusCode,
            httpStatusCode.getMessage()
        );
    }
}
