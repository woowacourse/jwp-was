import java.util.Arrays;

import controller.LoginController;
import controller.UserController;
import service.UserService;
import webserver.WebServer;

public class SlippApplication {
    public static void main(String[] args) throws Exception {
        UserService userService = new UserService();
        UserController userController = new UserController(userService);
        LoginController loginController = new LoginController(userService);
        WebServer.main(Arrays.asList(userController, loginController), args);
    }
}
