package controller;

import http.request.Request;
import http.request.RequestMethod;
import http.response.*;
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

    @Override
    public void processResponse(Request request, Response response) throws IOException, URISyntaxException {
        getFileResponse(request, response);
    }

    private void getFileResponse(Request request, Response response) throws IOException, URISyntaxException {
        response.setResponseStatus(ResponseStatus.OK);
        response.setResponseHeaders(new ResponseHeaders());
        response.addResponseHeaders("Content-Type: ", request.getUrl().getRequestContentType().getContentType());
        byte[] body = FileIoUtils.loadFileFromClasspath(request.getUrl().getDestinationFolderUrlPath());
        response.setResponseBody(body);
    }

    private boolean isAllowedPath(ControllerMapper controllerMapper) {
        return allowedUrlPaths.stream()
                .anyMatch(path -> controllerMapper.getOriginalUrlPath().contains(path));
    }

    private boolean isAllowedMethod(ControllerMapper controllerMapper) {
        return allowedMethods.stream()
                .anyMatch(method -> controllerMapper.getRequestMethod() == method);
    }
}
