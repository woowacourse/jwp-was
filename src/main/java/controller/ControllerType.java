package controller;

import java.util.Arrays;
import java.util.function.Predicate;

import http.request.HttpRequest;
import http.request.StaticType;
import http.request.TemplateType;

public enum ControllerType {

    TEMPLATE(ControllerType::isTemplate, new TemplateController()),
    STATIC(ControllerType::isStatic, new StaticController()),
    USER(ControllerType::isUser, new UserController());

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
                .orElseThrow(RuntimeException::new);
    }

    public Controller getController() {
        return this.controller;
    }

    private static boolean isTemplate(final HttpRequest httpRequest) {
        final String url = httpRequest.getUrl();
        final String[] splittedUrl = url.split("\\.");
        final TemplateType templateType = TemplateType.find(splittedUrl[splittedUrl.length - 1]);
        return !TemplateType.NONE.equals(templateType);
    }

    private static boolean isStatic(final HttpRequest httpRequest) {
        final String url = httpRequest.getUrl();
        String[] splittedUrl = url.split("\\.");
        final StaticType staticType = StaticType.find(splittedUrl[splittedUrl.length - 1]);
        return !StaticType.NONE.equals(staticType);
    }

    private static boolean isUser(HttpRequest httpRequest) {
        final String url = httpRequest.getUrl();
        return "/user/create".equals(url);
    }
}
