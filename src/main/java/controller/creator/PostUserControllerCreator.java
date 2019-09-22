package controller.creator;

import controller.Controller;
import controller.PostUserController;
import http.request.Request2;

public class PostUserControllerCreator implements ControllerCreator {

    @Override
    public Controller createController(Request2 request) {
        return new PostUserController();
    }
}
