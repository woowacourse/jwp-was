package was;

import was.controller.Controller;
import was.controller.CreateUserController;
import was.controller.MainController;
import webserver.http.request.HttpRequest;
import webserver.http.exception.NotFoundException;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {
    private static final Map<String, Controller> map = new HashMap<>();

    private HandlerMapping() {}

    static {
        map.put("/", new MainController());
        map.put("/user/create", new CreateUserController());
    }

    public static Controller getHandler(final HttpRequest httpRequest) {
        String fullUrl = httpRequest.getRequestLine().getFullUrl();

        if (map.containsKey(fullUrl)) {
            return map.get(fullUrl);
        }

        throw new NotFoundException();
    }
}
