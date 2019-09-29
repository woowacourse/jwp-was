package controller;

import controller.controllermapper.ControllerMapper;
import http.request.Request;
import http.request.RequestMethod;
import http.response.Response;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class FileController implements Controller {
    private List<RequestMethod> allowedMethods = Arrays.asList(RequestMethod.GET);
    private List<String> allowedUrlPaths = Arrays.asList(".html", ".css", ".html", ".ico", ".woff", ".ttf", ".js");

    @Override
    public boolean isMapping(ControllerMapper controllerMapper) {
        return (isAllowedMethod(controllerMapper) && isAllowedPath(controllerMapper));
    }

    private boolean isAllowedPath(ControllerMapper controllerMapper) {
        return allowedUrlPaths.stream()
                .anyMatch(path -> controllerMapper.getOriginalUrlPath().contains(path));
    }

    private boolean isAllowedMethod(ControllerMapper controllerMapper) {
        return allowedMethods.stream()
                .anyMatch(method -> controllerMapper.getRequestMethod() == method);
    }

    @Override
    public void processResponse(Request request, Response response) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(request.getUrl().getDestinationFolderUrlPath());

        response.ok()
                .putResponseHeaders("Content-Type: ", request.getUrl().getRequestContentType().getContentType())
                .body(body);
    }
}
