package webserver.controller;

import model.User;
import webserver.controller.annotation.Controller;
import webserver.controller.annotation.RequestMapping;
import webserver.request.MethodType;
import webserver.response.ModelAndView;
import webserver.service.UserService;

@Controller
public class UserController {

    @RequestMapping(value = "/user/create", type = MethodType.POST)
    public ModelAndView create(User user) {
        UserService userService = new UserService();
        userService.save(user);

        return ModelAndView.of("redirect:/index");
    }

    @RequestMapping(type = MethodType.GET, value = "/user/form")
    public ModelAndView form() {
        return ModelAndView.of("form");
    }
}
