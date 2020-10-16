package webserver;

import com.google.common.collect.Maps;
import controller.Controller;
import controller.ResourceController;
import controller.UserController;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import model.general.ContentType;
import model.request.HttpRequest;

public class ControllerMapper {

    private static final Map<String, Controller> controllerMap = Maps.newHashMap();
    private static final Controller resourceController = new ResourceController();

    static {
        controllerMap.put("/user/", new UserController());
    }

    public static Controller selectController(HttpRequest httpRequest) {
        Optional<ContentType> contentType = ContentType.of(httpRequest);
        if (contentType.isPresent()) {
            return resourceController;
        }

        String path = httpRequest.getRequestUri();
        for (Map.Entry<String, Controller> entry : controllerMap.entrySet()) {
            if (path.startsWith(entry.getKey())) {
                return controllerMap.get(entry.getKey());
            }
        }

        return null;
    }
}
