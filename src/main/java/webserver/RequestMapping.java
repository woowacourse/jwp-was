package webserver;

import controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(RequestMapping.class);

    private static Map<String, Controller> controllers = new HashMap<String, Controller>();
    private static Controller forwardController = new ForwardController();
    private static Controller staticController = new StaticController();

    static {
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/list", new ListUserController());
    }

    public static Controller getController(String requestUrl) {
        logger.debug("Request Mapping Url : {}", requestUrl);

        Controller controller = controllers.get(requestUrl);
        if (controller != null) {
            return controller;
        }

        if (requestUrl.endsWith(".html")) {
            return forwardController;
        }

        return staticController;
    }
}
