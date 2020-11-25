package application;

import controller.UserCreateController;
import controller.UserListController;
import controller.UserLoginController;
import controller.UserProfileController;
import java.util.Optional;
import utils.ControllerMapper;
import webserver.WebServer;

public class Application {

    private static final int NO_ARGUMENTS_SIZE = 0;

    public static void main(String[] args) throws Exception {
        ControllerMapper.addController("/user/create", new UserCreateController());
        ControllerMapper.addController("/user/login", new UserLoginController());
        ControllerMapper.addController("/user/list", new UserListController());
        ControllerMapper.addController("/user/profile", new UserProfileController());

        WebServer webServer = new WebServer();
        String userPort = getUserPort(args);

        webServer.run(userPort);
    }

    private static String getUserPort(String[] args) {
        if (args.length == NO_ARGUMENTS_SIZE) {
            return null;
        }

        return args[0];
    }
}
