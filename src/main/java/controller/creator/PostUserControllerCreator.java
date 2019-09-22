package controller.creator;

import controller.Controller2;
import controller.PostUserController;
import http.request.Request2;

public class PostUserControllerCreator implements Controller2Creator {
    @Override
    public Controller2 createController(Request2 request) {
        return new PostUserController();
    }
}
