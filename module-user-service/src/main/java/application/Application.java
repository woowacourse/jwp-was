package application;

import controller.UserCreateController;
import controller.UserListController;
import controller.UserLoginController;
import controller.UserProfileController;
import utils.ControllerMapper;
import webserver.WebServer;

public class Application {

    public static void main(String[] args) throws Exception {
        ControllerMapper.addController("/user/create", new UserCreateController());
        ControllerMapper.addController("/user/login", new UserLoginController());
        ControllerMapper.addController("/user/list", new UserListController());
        ControllerMapper.addController("/user/profile", new UserProfileController());

        WebServer webServer = new WebServer();
        webServer.run(args);
    }
}
