package jwp.was.webapplicationserver.controller;

import java.util.List;
import jwp.was.webapplicationserver.configure.annotation.Autowired;
import jwp.was.webapplicationserver.configure.annotation.Controller;
import jwp.was.webapplicationserver.configure.annotation.RequestMapping;
import jwp.was.webapplicationserver.configure.controller.ModelAndView;
import jwp.was.webapplicationserver.model.User;
import jwp.was.webapplicationserver.service.UserService;
import jwp.was.webserver.HttpMethod;
import jwp.was.webserver.dto.HttpRequest;

@Controller
public class UserPageController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = HttpMethod.GET, urlPath = "/user/list")
    public ModelAndView getUserListPage(HttpRequest httpRequest) {
        List<User> allUser = userService.findAllUser();
        return new ModelAndView(allUser, "/user/list");
    }
}
