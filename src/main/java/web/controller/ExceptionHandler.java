package web.controller;

import exception.CustomException;
import web.http.HttpResponse;
import web.http.HttpStatus;

public class ExceptionHandler {
    public static void processException(Exception e, HttpResponse httpResponse) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String exceptionMessage = e.getClass().equals(CustomException.class)
            ? e.getMessage() : HttpStatus.BAD_REQUEST.getMessage();
        byte[] body = exceptionMessage.getBytes();
        httpResponse.exception(httpStatus, body);
    }
}
