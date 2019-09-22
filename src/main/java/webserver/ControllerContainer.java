package webserver;

import com.google.common.collect.Maps;
import controller.AbstractController;
import controller.UserCreateController;

import java.util.Map;
import java.util.Optional;

public class ControllerContainer {
    private static Map<String, AbstractController> controllers = Maps.newHashMap();

    static {
        controllers.put("/user/create", new UserCreateController());
    }

    public static Optional<AbstractController> findController(String path) {
        return Optional.ofNullable(controllers.get(path));
    }
}
