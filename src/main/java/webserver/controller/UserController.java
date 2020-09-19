package webserver.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import model.User;
import webserver.RequestHeader;
import webserver.ServletResponse;
import webserver.UserService;
import webserver.controller.annotation.Controller;
import webserver.controller.annotation.RequestMapping;

@Controller
public class UserController implements Handlers {

    @RequestMapping(value = "/user/create", type = RequestHeader.MethodType.POST)
    public ServletResponse create(User user) {
        UserService userService = new UserService();
        userService.save(user);

        Map<String, String> response = new LinkedHashMap<>();
        response.put("Location", "http://localhost:8080/index.html");

        return new ServletResponse(ServletResponse.StatusCode.FOUND, response);
    }

    @RequestMapping(type = RequestHeader.MethodType.GET, value = "/user/form")
    public ServletResponse form() {
        LinkedHashMap<String, String> response = new LinkedHashMap<>();
        response.put("View", "form");

        return new ServletResponse(ServletResponse.StatusCode.OK, response);
    }
}
