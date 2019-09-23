package webserver.handler;

import webserver.handler.controller.Controller;
import webserver.handler.controller.custom.HomeController;
import webserver.handler.controller.custom.UserController;
import webserver.handler.controller.resource.ResourceController;
import webserver.handler.controller.resource.TemplateController;
import webserver.view.ViewResolverList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class ControllerList {
    private static final Controller[] CONCRETE_CONTROLLER = {
            new HomeController(ViewResolverList.TEMPLATE_RESOLVER),
            new ResourceController(ViewResolverList.STATIC_RESOLVER),
            new TemplateController(ViewResolverList.TEMPLATE_RESOLVER),
            new UserController(ViewResolverList.TEMPLATE_RESOLVER)
    };

    public static List<Controller> CONTROLLERS = Collections.unmodifiableList(Arrays.asList(CONCRETE_CONTROLLER));
}
