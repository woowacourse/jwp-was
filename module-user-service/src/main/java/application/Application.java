package application;

import com.google.common.collect.Maps;
import controller.Controller;
import controller.UserCreateController;
import controller.UserListController;
import controller.UserLoginController;
import controller.UserProfileController;
import java.util.Map;
import webserver.WebServer;

public class Application {

    private static final int NO_ARGUMENTS_SIZE = 0;

    public static void main(String[] args) throws Exception {
        WebServer webServer = new WebServer(setUpControllers());
        String userPort = getUserPort(args);

        webServer.run(userPort);
    }

    private static Map<String, Controller> setUpControllers() {
        Map<String, Controller> controllerMap = Maps.newHashMap();

        controllerMap.put("/user/create", new UserCreateController());
        controllerMap.put("/user/login", new UserLoginController());
        controllerMap.put("/user/list", new UserListController());
        controllerMap.put("/user/profile", new UserProfileController());

        return controllerMap;
    }

    private static String getUserPort(String[] args) {
        if (args.length == NO_ARGUMENTS_SIZE) {
            return null;
        }

        return args[0];
    }
}
