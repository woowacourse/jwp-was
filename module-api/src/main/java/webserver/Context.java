package webserver;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import controller.Controller;
import controller.FileController;
import controller.user.LoginController;
import controller.user.UserCreateController;
import controller.user.UserListController;
import controller.user.UserReadController;
import service.session.SessionService;
import service.user.UserService;

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
        controllers.put("/user/create", new UserCreateController(userService, sessionService));
        controllers.put("/user/profile", new UserReadController(userService, objectMapper));
        controllers.put("/user/login", new LoginController(userService));
        controllers.put("/user/list", new UserListController(userService, sessionService, objectMapper));
        return controllers;
    }
}
