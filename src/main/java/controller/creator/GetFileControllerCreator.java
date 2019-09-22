package controller.creator;

import controller.Controller2;
import controller.GetFileController;
import http.request.Request2;

public class GetFileControllerCreator implements Controller2Creator {

    @Override
    public Controller2 createController(Request2 request) {
        return new GetFileController();
    }
}
