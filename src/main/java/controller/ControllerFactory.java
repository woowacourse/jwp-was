package controller;

import http.request.GetRequest;
import http.request.PostRequest;
import http.request.Request;

import java.util.HashMap;
import java.util.Map;

public class ControllerFactory {
    private static Map<Class<? extends Request>, ControllerCreator> controllerCreators = new HashMap<>();

    static {
        controllerCreators.put(GetRequest.class, new FileControllerCreator());
        controllerCreators.put(PostRequest.class, new UserControllerCreator());
    }

    public static Controller getController(Request request) {
        ControllerCreator controllerCreator = controllerCreators.get(request.getClass());
        return controllerCreator.create(request);
    }

    static class FileControllerCreator implements ControllerCreator {
        @Override
        public Controller create(Request request) {
            return new FileController(request);
        }
    }

    static class UserControllerCreator implements ControllerCreator {
        @Override
        public Controller create(Request request) {
            return new PostDataController(request);
        }
    }
}
