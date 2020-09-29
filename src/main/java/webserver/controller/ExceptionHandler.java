package webserver.controller;

import exception.CustomException;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseHeader;
import webserver.http.response.HttpResponseLine;
import webserver.http.response.HttpStatus;

public class ExceptionHandler {
    public static HttpResponse processException(Exception e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String exceptionMessage = e.getClass().equals(CustomException.class)
                ? e.getMessage() : HttpStatus.BAD_REQUEST.getMessage();
        byte[] body = exceptionMessage.getBytes();

        HttpResponseHeader httpResponseHeader = new HttpResponseHeader(new HttpResponseLine(httpStatus));
        httpResponseHeader.add("Content-Type", "text/html;charset=utf-8");
        httpResponseHeader.add("Content-Length", String.valueOf(body.length));

        return new HttpResponse(httpResponseHeader, body);
    }
}
