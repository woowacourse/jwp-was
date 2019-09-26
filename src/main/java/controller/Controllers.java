package controller;

import annotation.RequestMapping;
import controller.custom.HomeController;
import controller.custom.UserController;
import controller.resources.ResourceController;
import controller.resources.TemplateController;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Controllers {
    public static final Map<String, Method> REQUEST_MAPPING_METHODS = new HashMap<>();
    public static final Map<Method, Controller> REQUEST_MAPPING_CONTROLLERS = new HashMap<>();

    private static final Controller[] CONTROLLERS = {
            new ResourceController(),
            new TemplateController(),
            new UserController(),
            new HomeController(),
    };

    static {
        Arrays.stream(CONTROLLERS)
                .forEach(controller -> Arrays.stream(controller.getClass().getDeclaredMethods())
                        .forEach(method -> mappingMethodAndController(controller, method)));
    }

    private static void mappingMethodAndController(Controller controller, Method method) {
        if (method.isAnnotationPresent(RequestMapping.class)) {
            REQUEST_MAPPING_METHODS.put(method.getAnnotation(RequestMapping.class).url(), method);
            REQUEST_MAPPING_CONTROLLERS.put(method, controller);
        }
    }

}
