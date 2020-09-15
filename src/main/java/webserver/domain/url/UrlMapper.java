package webserver.domain.url;

import java.util.HashMap;
import java.util.Map;
import webserver.controller.Controller;
import webserver.controller.CreateUserController;
import webserver.controller.StaticController;
import webserver.exception.UrlNotFoundException;

public class UrlMapper {

    private static final Map<String, Controller> mapper;

    static {
        mapper = new HashMap<>();
        mapper.put("/user/create", new CreateUserController());
        mapper.put("/", new StaticController());
    }

    public static Controller findMatchingController(String path) {
        if (mapper.containsKey(path)) {
            return mapper.get(path);
        }
        throw new UrlNotFoundException(path);
    }
}
