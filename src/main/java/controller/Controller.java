package controller;

import controller.controllermapper.ControllerMapper;
import http.request.Request;
import http.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {

    boolean isMapping(ControllerMapper controllerMapper);

    void processResponse(Request request, Response response) throws IOException, URISyntaxException;
}
