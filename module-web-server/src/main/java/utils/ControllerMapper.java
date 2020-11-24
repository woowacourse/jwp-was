package utils;

import com.google.common.collect.Maps;
import controller.Controller;
import controller.NotFoundController;
import controller.ResourceController;
import java.util.Map;
import model.request.HttpRequest;

public class ControllerMapper {

    private static final Map<String, Controller> controllerMap = Maps.newHashMap();
    private static final Controller resourceController = new ResourceController();
    private static final Controller notFoundController = new NotFoundController();

    public static Controller selectController(HttpRequest httpRequest) {
        if (httpRequest.whetherUriHasExtension()) {
            return resourceController;
        }

        String path = httpRequest.getRequestUri();
        for (Map.Entry<String, Controller> entry : controllerMap.entrySet()) {
            if (path.startsWith(entry.getKey())) {
                return controllerMap.get(entry.getKey());
            }
        }

        return notFoundController;
    }

    public static void addController(String location, Controller controller) {
        controllerMap.put(location, controller);
    }
}
