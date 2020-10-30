package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import type.resource.FileType;
import type.resource.ResourceType;

public class ControllerMapper {

    private static final String PERIOD = "\\.";

    private static final Map<String, Controller> controllers = new HashMap<>();
    private static final Controller templateController = new TemplateController();
    private static final Controller staticController = new StaticController();

    static {
        controllers.put("/user/create", new UserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/list", new UserListController());
    }

    private ControllerMapper() {}

    public static Controller find(final String url) {
        final Controller controller = controllers.get(url);

        if (Objects.isNull(controller)) {
            return findResourceController(url);
        }
        return controller;
    }

    private static Controller findResourceController(final String url) {
        final String[] splittedUrl = url.split(PERIOD);
        final FileType fileType = FileType.find(splittedUrl[splittedUrl.length - 1]);

        if (ResourceType.TEMPLATE == fileType.getResourceType()) {
            return templateController;
        }
        return staticController;
    }
}
