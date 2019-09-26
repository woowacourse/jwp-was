package servlet;

import http.HttpResponse;
import http.request.HttpRequest;
import servlet.controller.Controller;
import servlet.controller.ControllerFinder;

import java.io.IOException;
import java.net.URISyntaxException;

public class ServletContainer {
    private final ControllerFinder controllerMapper;

    public ServletContainer(ControllerFinder controllerMapper) {
        this.controllerMapper = controllerMapper;
    }

    public HttpResponse process(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        Controller controller = controllerMapper.find(httpRequest);
        controller.service(httpRequest, httpResponse);
        return httpResponse;
    }
}
