package jwp.was.webapplicationserver.controller;

import dto.HttpRequest;
import java.util.List;
import jwp.was.webapplicationserver.configure.annotation.Autowired;
import jwp.was.webapplicationserver.configure.annotation.Controller;
import jwp.was.webapplicationserver.configure.annotation.RequestMapping;
import jwp.was.webapplicationserver.configure.controller.info.ModelAndView;
import jwp.was.webapplicationserver.model.User;
import jwp.was.webapplicationserver.service.UserService;
import util.HttpMethod;

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
