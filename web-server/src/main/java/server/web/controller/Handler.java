package server.web.controller;

import server.web.request.RequestMapping;

public class Handler {
    private final RequestMapping requestMapping;
    private final Controller controller;

    public Handler(RequestMapping requestMapping, Controller controller) {
        this.requestMapping = requestMapping;
        this.controller = controller;
    }

    public static Handler of(String sourcePath) {
        try {
            Class<?> maybeController = Class.forName(sourcePath);
            server.web.controller.RequestMapping annotation = maybeController.getAnnotation(server.web.controller.RequestMapping.class);

            return new Handler(new server.web.request.RequestMapping(annotation.uri(), annotation.httpMethod()),
                    (Controller) maybeController.newInstance());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new IllegalArgumentException("컨트롤러가 아닙니다.");
        }
    }

    public RequestMapping getRequestMapping() {
        return requestMapping;
    }

    public Controller getController() {
        return controller;
    }
}
