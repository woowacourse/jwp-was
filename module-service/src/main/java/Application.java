import java.util.Arrays;

import controller.LoginController;
import controller.UserController;
import controller.UserListController;
import dto.UrlMappingController;
import webserver.WebServer;

public class Application {

    public static void main(String[] args) throws Exception {
        WebServer webServer = new WebServer(Arrays.asList(
                UrlMappingController.of("/user/create", new UserController()),
                UrlMappingController.of("/user/login", new LoginController()),
                UrlMappingController.of("/user/list", new UserListController())
        ));
        webServer.run(args);
    }
}
