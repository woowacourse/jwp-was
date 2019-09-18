package webserver;

import com.google.common.collect.Maps;
import controller.AbstractController;
import controller.UserCreateController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

public class ControllerContainer {
    private static Map<String, AbstractController> controllers = Maps.newHashMap();

    static {
        controllers.put("/user/create", new UserCreateController());
    }

    private static Optional<AbstractController> findController(String path) {
        return Optional.ofNullable(controllers.get(path));
    }

    public static void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        Optional<AbstractController> maybeController = findController(httpRequest.getPath());
        if (maybeController.isPresent()) {
            maybeController.get().service(httpRequest, httpResponse);
            return;
        }
        httpResponse.addHeader(httpRequest.getPath());
        httpResponse.addBody(httpRequest.getPath());
    }
}
