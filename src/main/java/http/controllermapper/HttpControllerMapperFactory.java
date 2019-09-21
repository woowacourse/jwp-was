package http.controllermapper;

import http.request.RequestInformation;

public class HttpControllerMapperFactory {

    public static ControllerMapper getControllerMapper(RequestInformation requestInformation) {
        String method = requestInformation.getMethod();
        String path = requestInformation.getUrl();
        String mapper = method + ":" + path;
        ControllerMapperRepository controllerMapperRepository = new ControllerMapperRepository();
        return ControllerMapperRepository.getController(mapper);
    }
}
