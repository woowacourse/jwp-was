package controller.type;

import java.util.Arrays;
import java.util.function.Predicate;

import controller.Controller;
import controller.StaticController;
import controller.type.resource.StaticType;
import controller.TemplateController;
import controller.type.resource.TemplateType;
import controller.UserController;
import exception.NotFoundControllerTypeException;
import http.request.HttpRequest;

public enum ControllerType {

    TEMPLATE(ControllerType::isTemplate, new TemplateController()),
    STATIC(ControllerType::isStatic, new StaticController()),
    USER(ControllerType::isUser, new UserController());

    private static final String PERIOD = "\\.";

    private final Predicate<HttpRequest> predicateControllerType;

    private final Controller controller;
    ControllerType(final Predicate<HttpRequest> predicateControllerType, final Controller controller) {
        this.predicateControllerType = predicateControllerType;
        this.controller = controller;
    }

    public static ControllerType find(final HttpRequest httpRequest) {
        return Arrays.stream(ControllerType.values())
                .filter(type -> type.predicateControllerType.test(httpRequest))
                .findFirst()
                .orElseThrow(() -> new NotFoundControllerTypeException("Controller를 찾을 수 없습니다."));
    }

    public Controller getController() {
        return this.controller;
    }

    private static boolean isTemplate(final HttpRequest httpRequest) {
        final String[] splittedUrl = splitUrlByPeriod(httpRequest);
        final TemplateType templateType = TemplateType.find(splittedUrl[splittedUrl.length - 1]);
        return !TemplateType.NONE.equals(templateType);
    }

    private static boolean isStatic(final HttpRequest httpRequest) {
        String[] splittedUrl = splitUrlByPeriod(httpRequest);
        final StaticType staticType = StaticType.find(splittedUrl[splittedUrl.length - 1]);
        return !StaticType.NONE.equals(staticType);
    }

    private static String[] splitUrlByPeriod(final HttpRequest httpRequest) {
        final String url = httpRequest.getUrl();
        return url.split(PERIOD);
    }

    private static boolean isUser(final HttpRequest httpRequest) {
        final String url = httpRequest.getUrl();
        return "/user/create".equals(url);
    }
}
