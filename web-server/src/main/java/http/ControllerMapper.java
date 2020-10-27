package http;

import http.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class ControllerMapper {
    private final Map<String, Servlet> controllers = new HashMap<>();

    private ControllerMapper() {
    }

    public static ControllerMapper getInstance() {
        return Holder.INSTANCE;
    }

    public void addController(AbstractServlet controller) {
        controllers.put(controller.getPath(), controller);
    }

    public Servlet map(HttpRequest httpRequest) {
        return controllers.get(httpRequest.getPath());
    }

    public boolean isApi(HttpRequest httpRequest) {
        return controllers.containsKey(httpRequest.getPath());
    }

    private static class Holder {
        private static final ControllerMapper INSTANCE = new ControllerMapper();
    }
}
