package webserver.controller;

import model.User;
import webserver.RequestHeader;
import webserver.controller.annotation.Controller;
import webserver.controller.annotation.RequestMapping;

@Controller
public class UserController implements Handlers {

    @RequestMapping(value = "/create/user", type = RequestHeader.MethodType.POST)
    public void create(User user) {
        System.out.println("dd");
    }
}
