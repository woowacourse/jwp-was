package webserver;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import controller.Controller;
import controller.FileController;
import domain.user.service.UserService;
import domain.user.web.LoginController;
import domain.user.web.UserCreateController;
import domain.user.web.UserListController;
import domain.user.web.UserReadController;
import session.service.SessionService;

public class Context {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Context instance = new Context();

    private Context() {
    }

    public static Context getInstance() {
        return instance;
    }

    public Map<String, Controller> createControllerBifurcation() {
        Map<String, Controller> controllers = new HashMap<>();
        UserService userService = UserService.getInstance();
        SessionService sessionService = SessionService.getInstance();

        controllers.put("file", new FileController());
        controllers.put("/user/create", new UserCreateController(userService));
        controllers.put("/user/profile", new UserReadController(userService, objectMapper));
        controllers.put("/user/login", new LoginController(userService, sessionService));
        controllers.put("/user/list", new UserListController(userService, sessionService, objectMapper));
        return controllers;
    }
}
