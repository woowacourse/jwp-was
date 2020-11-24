package user;

import java.io.IOException;

import user.controller.UserCreateController;
import user.controller.UserListController;
import user.controller.UserLoginController;
import webserver.WebServer;
import webserver.controller.ControllerMapping;
import webserver.http.request.HttpMethod;
import webserver.http.request.RequestMapping;

public class UserApplication {
    public static void main(String[] args) throws IOException {
        initControllerMappings();
        new WebServer(args).run();
    }

    private static void initControllerMappings() {
        ControllerMapping.put(new RequestMapping("/user/create", HttpMethod.POST),
                new UserCreateController());
        ControllerMapping.put(new RequestMapping("/user/login", HttpMethod.POST),
                new UserLoginController());
        ControllerMapping.put(new RequestMapping("/user/list.html", HttpMethod.GET),
                new UserListController());
    }
}
