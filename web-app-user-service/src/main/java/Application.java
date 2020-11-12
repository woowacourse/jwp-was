import controller.HttpRequestController;
import controller.LoginController;
import controller.ResourceController;
import controller.UserCreateController;
import controller.UserListController;
import http.request.HttpRequestMapping;
import webserver.WebServer;

public class Application {

    public static void main(String[] args) throws Exception {
        HttpRequestController.addDefaultController(new ResourceController(HttpRequestMapping.GET("/")));
        UserCreateController userCreateController = new UserCreateController(HttpRequestMapping.POST("/user/create"));
        LoginController loginController = new LoginController(HttpRequestMapping.POST("/user/login"));
        UserListController userListController = new UserListController(HttpRequestMapping.GET("/user/list"));

        HttpRequestController.addController(userCreateController);
        HttpRequestController.addController(loginController);
        HttpRequestController.addController(userListController);

        WebServer.run(args);
    }
}
