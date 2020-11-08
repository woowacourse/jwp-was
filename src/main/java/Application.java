import controller.*;

public class Application {
    public static void main(String[] args) throws Exception {
        ControllerFinder.addDefaultController(new ReadResourceController());
        ControllerFinder.addController("/user/create", new CreateUserController());
        ControllerFinder.addController("/user/login", new LoginController());
        ControllerFinder.addController("/user/list", new GetUserListController());

        WebServer.run(args);
    }
}
