package controller.creator;

import controller.Controller;
import controller.UserController;
import http.request.Request;

public class UserControllerCreator implements ControllerCreator {

    @Override
    public Controller createController(Request request) {
        return new UserController();
    }
}
