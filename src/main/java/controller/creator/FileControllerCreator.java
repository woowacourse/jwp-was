package controller.creator;

import controller.Controller;
import controller.FileController;
import http.request.Request;

public class FileControllerCreator implements ControllerCreator {

    @Override
    public Controller createController(Request request) {
        return new FileController();
    }
}
