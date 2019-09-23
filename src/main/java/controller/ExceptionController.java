package controller;

import http.request.Request;
import http.response.ExceptionResponse;
import http.response.Response;

public class ExceptionController implements Controller {

    @Override
    public boolean isMapping(ControllerMapper controllerMapper) {
        return false;
    }

    @Override
    public Response createResponse(Request request) {
        return new ExceptionResponse();
    }
}
