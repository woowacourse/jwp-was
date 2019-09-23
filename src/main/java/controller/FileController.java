package controller;

import http.request.Request;
import http.request.RequestMethod;
import http.response.FileResponse;
import http.response.Response;

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
    public Response createResponse(Request request) {
        return createGetResponse(request);
    }

    public Response createGetResponse(Request request) {
        return new FileResponse(request.getUrl().getDestinationFolderUrlPath(), request.getUrl().getRequestContentType().getContentType());
    }

//    private boolean isCorrectRequestContentType(Request request) {
//        return Arrays.stream(RequestContentType.values())
//                .anyMatch(type -> request.getUrl().getRequestContentType() == type);
//    }

}
