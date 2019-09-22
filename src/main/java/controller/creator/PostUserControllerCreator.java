package controller.creator;

import controller.Controller;
import controller.PostUserController;
import http.request.Request;

public class PostUserControllerCreator implements ControllerCreator {

    @Override
    public Controller createController(Request request) {
        return new PostUserController();
    }
}
