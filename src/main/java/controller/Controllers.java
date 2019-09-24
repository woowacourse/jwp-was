package controller;

import controller.custom.HomeController;
import controller.custom.UserController;
import controller.resources.ResourceController;
import controller.resources.TemplateController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Controllers {
    private static final Controller[] CONTROLLERS = {
            new HomeController(),
            new UserController(),
            new TemplateController(),
            new ResourceController(),
    };

    public static List<Controller> LIST = Collections.unmodifiableList(Arrays.asList(CONTROLLERS));
}
