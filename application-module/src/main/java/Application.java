import controller.CreateUserController;
import controller.ListController;
import controller.RootController;
import controller.UserLoginController;
import domain.UrlMapper;
import domain.controller.Controller;
import domain.controller.FrontController;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final Controller CONTROLLER;

    static {
        Map<String, Controller> urlMapper = new HashMap<>();
        urlMapper.put("/user/create", CreateUserController.getInstance());
        urlMapper.put("/user/login", UserLoginController.getInstance());
        urlMapper.put("/", RootController.getInstance());
        urlMapper.put("/user/list", ListController.getInstance());
        CONTROLLER = new FrontController(new UrlMapper(urlMapper));
    }

    public static void main(String[] args) throws Exception {
        try {
            WebServer.main(args, CONTROLLER);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
