package client;

import client.controller.*;
import was.webserver.WebServer;
import was.webserver.controller.Controller;

import java.util.Arrays;
import java.util.List;

public class ClientApplication {
    public static void main(String[] args) throws Exception {
        List<Controller> controllers = Arrays.asList(
                new IndexController(),
                new ResourceController(),
                new UserCreateController(),
                new UserListController(),
                new UserLoginController()
        );

        WebServer.main(args, controllers);
    }
}
