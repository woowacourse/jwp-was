package servlet;

import http.request.HttpRequest;
import http.response.HttpResponse;
import servlet.controller.Controller;
import servlet.controller.ControllerFinder;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpRequestHandler {
    private final ControllerFinder controllerMapper;

    public HttpRequestHandler(final ControllerFinder controllerMapper) {
        this.controllerMapper = controllerMapper;
    }

    public void process(
            final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException, URISyntaxException {
        Controller controller = controllerMapper.find(httpRequest);
        controller.service(httpRequest, httpResponse);
    }
}
