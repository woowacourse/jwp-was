package controller.creator;

import controller.Controller;
import controller.GetFileController;
import http.request.Request;

public class GetFileControllerCreator implements ControllerCreator {

    @Override
    public Controller createController(Request request) {
        return new GetFileController();
    }
}
