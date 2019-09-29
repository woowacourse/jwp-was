package http.servlet;

import com.google.common.base.Charsets;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.servlet.controller.Controller;
import http.servlet.controller.ControllerFinder;
import http.servlet.controller.exception.Page404NotFoundException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;

public class HttpServletHandler {
    private final ControllerFinder controllerMapper;

    public HttpServletHandler(final ControllerFinder controllerMapper) {
        this.controllerMapper = controllerMapper;
    }

    public void process(
            final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException, URISyntaxException {
        try {
            Controller controller = controllerMapper.find(httpRequest);
            controller.service(httpRequest, httpResponse);
        } catch (final Page404NotFoundException e) {
            String message = URLDecoder.decode(e.getMessage(), "UTF-8");
            httpResponse.pageNotFound(message.getBytes(Charsets.UTF_8));
        }
    }
}
