package web.controller;

import webserver.controller.RequestMapper;

public class ControllerMapping extends RequestMapper {
    static {
        get("/", IndexController.getInstance().goIndex());
        get("/user/form", UserController.getInstance().goForm());
        post("/user/create", UserController.getInstance().createUser());
    }
}
