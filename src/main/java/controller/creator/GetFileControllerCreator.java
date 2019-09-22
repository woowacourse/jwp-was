package controller.creator;

import controller.Controller;
import controller.GetFileController;
import http.request.Request2;

public class GetFileControllerCreator implements ControllerCreator {

    @Override
    public Controller createController(Request2 request) {
        return new GetFileController();
    }
}
