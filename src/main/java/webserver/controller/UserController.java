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

    @RequestMapping(value = "/create/user", type = RequestHeader.MethodType.POST)
    public ServletResponse create(User user) {
        UserService userService = new UserService();
        userService.save(user);

        Map<String, String> response = new LinkedHashMap<>();
        response.put("Location", "http://localhost:8080/index.html");

        return new ServletResponse(ServletResponse.StatusCode.FOUND, response);
    }
}
