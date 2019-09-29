package http.servlet;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.servlet.controller.Controller;
import http.servlet.controller.ControllerFinder;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpServletHandler {
    private final ControllerFinder controllerMapper;

    public HttpServletHandler(final ControllerFinder controllerMapper) {
        this.controllerMapper = controllerMapper;
    }

    public void process(
            final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException, URISyntaxException {
        Controller controller = controllerMapper.find(httpRequest);
        controller.service(httpRequest, httpResponse);
    }
}
