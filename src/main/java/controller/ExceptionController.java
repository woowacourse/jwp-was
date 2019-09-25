package controller;

import http.request.Request;
import http.response.ExceptionResponse;
import http.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public class ExceptionController implements Controller {

    @Override
    public boolean isMapping(ControllerMapper controllerMapper) {
        return false;
    }

    @Override
    public void processResponse(Request request, Response response) throws IOException, URISyntaxException {

    }
}
