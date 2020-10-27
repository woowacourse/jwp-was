import controller.LoginController;
import controller.UserController;
import controller.UserListController;
import http.AbstractServlet;
import webserver.WebServer;

import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args) throws Exception {
        List<AbstractServlet> controllers = Arrays.asList(
                new LoginController(),
                new UserController(),
                new UserListController()
        );
        WebServer.main(args, controllers);
    }
}
