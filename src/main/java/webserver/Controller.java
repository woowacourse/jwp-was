package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import model.User;
import utils.RequestUtils;
import webserver.request.Request;
import webserver.request.body.RequestBody;
import webserver.response.Response;

public class Controller {
    public static final String DEFAULT = "default";

    private final Map<String, Function<Request, Response>> handlers;

    public Controller() {
        handlers = new HashMap<>();
        handlers.put("/user/create", this::createUser);
        handlers.put(DEFAULT, this::serveDefaultPage);
    }

    public Response handle(Request request) {
        String path = RequestUtils.trimExtensionIfExists(request.getPath());
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
        RequestBody body= request.getBody();
        User user = new User(
            body.get("userId"),
            body.get("password"),
            body.get("name"),
            body.get("email")
        );
        return Response.found(request, "/index.html");
    }

    private Response serveDefaultPage(Request request) {
        return Response.ok(request);
    }
}
