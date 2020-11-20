package server.utils;

import server.web.Handler;
import server.web.controller.Controller;
import server.web.controller.RequestMapping;

import java.util.Arrays;
import java.util.Objects;

public class HandlerFactory {

    public static Handler create(String sourcePath) {
        try {
            Class<?> maybeController = Class.forName(sourcePath);
            RequestMapping annotation = maybeController.getAnnotation(RequestMapping.class);

            return new Handler(new server.web.request.RequestMapping(annotation.uri(), annotation.httpMethod()),
                    (Controller) maybeController.newInstance());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new IllegalArgumentException("컨트롤러가 아닙니다.");
        }
    }

    public static boolean isController(String sourcePath) {
        Class<?> maybeController = null;
        try {
            maybeController = Class.forName(sourcePath);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("존재하지 않는 클래스 파일입니다.");
        }

        boolean isControllerType = Arrays.stream(maybeController.getInterfaces())
                .anyMatch(aInterface -> aInterface.getName().equals(Controller.class.getName()));
        RequestMapping annotation = maybeController.getAnnotation(RequestMapping.class);
        return isControllerType && Objects.nonNull(annotation);
    }
}
