package was;

import was.controller.*;
import webserver.http.exception.NotFoundException;
import webserver.http.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {
    private static final Map<String, Controller> map = new HashMap<>();

    private HandlerMapping() {}

    static {
        map.put("/", MainController.getInstance());
        map.put("/user/create", CreateUserController.getInstance());
        map.put("/user/login", LoginUserController.getInstance());
        map.put("/user/list", ListUserController.getInstance());
    }

    public static Controller getHandler(final HttpRequest httpRequest) {
        String fullUrl = httpRequest.getRequestLine().getFullUrl();

        if (map.containsKey(fullUrl)) {
            return map.get(fullUrl);
        }

        throw new NotFoundException();
    }
}
