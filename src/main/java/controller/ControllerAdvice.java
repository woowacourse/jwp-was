package controller;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

import java.util.HashMap;
import java.util.Map;
import webserver.HttpStatusCode;
import webserver.dto.HttpRequest;
import webserver.dto.HttpResponse;

public class ControllerAdvice {

    private static final String JSON_EXCEPTION_MESSAGE_FORMAT = "{ \"ExceptionMessage\" : \"%s\"}";

    public HttpResponse handleException(HttpRequest httpRequest, Exception exception) {
        if (exception instanceof IllegalArgumentException
            || exception instanceof NullPointerException) {
            return handleException(httpRequest, HttpStatusCode.BAD_REQUEST);
        }
        return handleException(httpRequest, HttpStatusCode.INTERNAL_SERVER_ERROR);
    }

    public HttpResponse handleException(HttpRequest httpRequest, HttpStatusCode httpStatusCode) {
        return HttpResponse.of(
            httpRequest.getProtocol(),
            httpStatusCode,
            getHeadersContentTypeJson(),
            makeJsonBodyHttpStatusMessage(httpStatusCode)
        );
    }

    private Map<String, String> getHeadersContentTypeJson() {
        Map<String, String> header = new HashMap<>();
        header.put(CONTENT_TYPE, "application/json");
        return header;
    }

    private String makeJsonBodyHttpStatusMessage(HttpStatusCode httpStatusCode) {
        return String.format(JSON_EXCEPTION_MESSAGE_FORMAT, httpStatusCode.getMessage());
    }
}
