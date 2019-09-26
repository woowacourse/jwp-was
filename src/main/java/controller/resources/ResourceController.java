package controller.resources;

import annotation.RequestMapping;
import controller.Controller;
import model.http.HttpRequest;
import model.http.HttpResponse;
import model.http.ViewLocation;
import utils.HttpMethod;
import utils.HttpStatus;

public class ResourceController implements Controller {
    private static final String TEMPLATE_FILE_EXTENSION = ".html";
    private static final String RESOURCE_FILE_DELIMITER = ".";

    @RequestMapping(method = HttpMethod.GET, url = "static")
    public void staticResourceRequest(HttpRequest request, HttpResponse response) {
        response.sendRedirect(ViewLocation.STATIC.getLocation() + request.getPath(), HttpStatus.OK);
    }
}
