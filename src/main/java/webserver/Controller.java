package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import model.User;
import utils.StringUtils;

public class Controller {
    public static final String DEFAULT = "default";

    private final Map<String, Function<Request, Response>> handlers;

    public Controller() {
        handlers = new HashMap<>();
        handlers.put("/user/create", this::createUser);
        handlers.put(DEFAULT, this::serveDefaultPage);
    }

    public Response handle(Request request) {
        String path = StringUtils.trimExtensionIfExists(request.getPath());
        String handlerName = handlers.keySet()
            .stream()
            .filter(route -> route.equals(path))
            .findFirst()
            .orElse(DEFAULT);
        return handlers
            .get(handlerName)
            .apply(request);
    }

    private Response createUser(Request request) {
        Map<String, String> body = request.getBody();
        User user = new User(
            body.get("userId"),
            body.get("password"),
            body.get("name"),
            body.get("email")
        );
        System.out.println(user);
        return Response.of("/index.html");
    }

    private Response serveDefaultPage(Request request) {
        return Response.of(request);
    }
}
