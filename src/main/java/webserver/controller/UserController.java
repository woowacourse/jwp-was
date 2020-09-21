package webserver.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import model.User;
import webserver.request.MethodType;
import webserver.response.ModelAndView;
import webserver.response.StatusCode;
import webserver.service.UserService;
import webserver.controller.annotation.Controller;
import webserver.controller.annotation.RequestMapping;

@Controller
public class UserController implements Handlers {

    @RequestMapping(value = "/user/create", type = MethodType.POST)
    public ModelAndView create(User user) {
        UserService userService = new UserService();
        userService.save(user);

        Map<String, String> headers = new LinkedHashMap<>();
        Map<String, String> attributes = new LinkedHashMap<>();
        headers.put("Location", "http://localhost:8080/index.html");

        return ModelAndView.of(StatusCode.FOUND, headers, attributes, "index");
    }

    @RequestMapping(type = MethodType.GET, value = "/user/form")
    public ModelAndView form() {
        LinkedHashMap<String, String> headers = new LinkedHashMap<>();
        Map<String, String> attributes = new LinkedHashMap<>();

        return ModelAndView.of(StatusCode.OK, headers, attributes, "form");
    }
}
