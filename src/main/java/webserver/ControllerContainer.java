package webserver;

import com.google.common.collect.Maps;
import controller.AbstractController;
import controller.UserCreateController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.view.DefaultView;

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
        if (controllers.keySet().contains(httpRequest.getPath())) {
            controllers.get(httpRequest.getPath()).service(httpRequest, httpResponse);
            return;
        }
        httpResponse.render(new DefaultView(httpRequest.getPath()));
    }
}
