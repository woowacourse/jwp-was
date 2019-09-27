package controller.methods;

import http.request.Request;
import http.request.RequestMethod;
import http.response.Response;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class GetFileControllerMethod implements ControllerMethod {
    private List<RequestMethod> allowedMethods = Arrays.asList(RequestMethod.GET);
    private List<String> allowedUrlPaths = Arrays.asList(".html", ".css", ".html", ".ico", ".woff", ".ttf", ".js");

    @Override
    public boolean isMapping(Request request) {
        return isAllowedMethod(request) && isAllowedUrlPaths(request);
    }

    private boolean isAllowedUrlPaths(Request request) {
        return allowedUrlPaths.stream()
                .anyMatch(url -> request.getUrl().getOriginalUrlPath().contains(url));
    }

    private boolean isAllowedMethod(Request request) {
        return allowedMethods.stream()
                .anyMatch(method -> request.getRequestMethod() == method);
    }

    @Override
    public void processResponse(Request request, Response response) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(request.getUrl().getDestinationFolderUrlPath());

        response.ok()
                .putResponseHeaders("Content-Type: ", request.getUrl().getRequestContentType().getContentType())
                .body(body);
    }
}
