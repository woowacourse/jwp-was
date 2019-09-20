package controller;

import http.request.GetRequest;
import http.request.PostRequest;
import http.request.Request;

import java.util.HashMap;
import java.util.Map;

public class ControllerFactory {
    private static Map<Class<? extends Request>, ControllerCreator> controllerCreators = new HashMap<>();
    private static Map<String, String> parameters;

    static {
        controllerCreators.put(GetRequest.class, new FileControllerCreator());
        controllerCreators.put(PostRequest.class, new DataControllerCreator());
    }

    public static Controller getController(Request request, Map<String, String> params) {
        parameters = params;
        ControllerCreator controllerCreator = controllerCreators.get(request.getClass());
        return controllerCreator.create(request);
    }

    static class FileControllerCreator implements ControllerCreator {
        @Override
        public Controller create(Request request) {
            return new FileController(request);
        }
    }

    static class DataControllerCreator implements ControllerCreator {
        @Override
        public Controller create(Request request) {
            Controller controller =  new PostDataController(request, parameters);
            parameters.clear();
            return controller;
        }
    }
}
