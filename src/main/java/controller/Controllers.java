package controller;

import controller.annotation.RequestMapping;
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

    private static final Controller[] CONTROLLERS = {
            new ResourceController(),
            new TemplateController(),
            new UserController(),
            new HomeController(),
    };

    static {
        Arrays.stream(CONTROLLERS)
                .forEach(controller -> Arrays.stream(controller.getClass().getDeclaredMethods())
                        .forEach(Controllers::mappingMethodAndController));
    }

    private static void mappingMethodAndController(Method method) {
        if (method.isAnnotationPresent(RequestMapping.class)) {
            REQUEST_MAPPING_METHODS.put(method.getAnnotation(RequestMapping.class).url(), method);
        }
    }
}
