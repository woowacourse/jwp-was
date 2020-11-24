package server.web.controller;

import server.web.request.RequestMapping;

public class Handler {
    private final RequestMapping requestMapping;
    private final Controller controller;

    public Handler(RequestMapping requestMapping, Controller controller) {
        this.requestMapping = requestMapping;
        this.controller = controller;
    }

    public static Handler of(String classPath) {
        try {
            Class<?> maybeController = Class.forName(classPath);
            server.web.controller.RequestMapping annotation = maybeController.getAnnotation(server.web.controller.RequestMapping.class);

            return new Handler(new server.web.request.RequestMapping(annotation.uri(), annotation.httpMethod()),
                    (Controller) maybeController.newInstance());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(String.format("%s : 클래스가 존재하지 않습니다.", classPath));
        } catch (IllegalAccessException | InstantiationException e) {
            throw new IllegalArgumentException(String.format("인스턴스를 생성할 수 없습니다. : %s", e.getMessage()));
        }
    }

    public RequestMapping getRequestMapping() {
        return requestMapping;
    }

    public Controller getController() {
        return controller;
    }
}
