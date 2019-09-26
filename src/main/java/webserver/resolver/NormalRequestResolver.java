package webserver.resolver;

import controller.Controller;
import controller.LoginController;
import controller.UserController;
import controller.UserListController;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NormalRequestResolver {
    private static final Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/user/create", new UserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/list", new UserListController());
    }

    static void resolve(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String path = httpRequest.getPath();
        Controller controller = Optional.ofNullable(controllers.get(path)).orElseThrow(BadRequestException::new);

        controller.service(httpRequest, httpResponse);
    }
}
