package controller.creator;

import controller.Controller2;
import controller.UserController2;
import http.request.Request2;

public class UserController2Creator implements Controller2Creator{
    @Override
    public Controller2 createController(Request2 request) {
        return new UserController2(request);
    }
}
