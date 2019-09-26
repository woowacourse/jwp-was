package webserver;

import com.google.common.collect.Maps;
import controller.AbstractController;
import controller.UserCreateController;
import controller.UserListController;
import controller.UserLoginController;

import java.util.Map;
import java.util.Optional;

public class ControllerContainer {
    private static Map<String, AbstractController> controllers = Maps.newHashMap();

    static {
        controllers.put("/user/createSession", new UserCreateController());
        controllers.put("/user/login", new UserLoginController());
        controllers.put("/user/list", new UserListController());
    }

    public static Optional<AbstractController> findController(String path) {
        return Optional.ofNullable(controllers.get(path));
    }
}
