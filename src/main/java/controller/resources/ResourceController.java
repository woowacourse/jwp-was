package controller.resources;

import controller.AbstractController;
import model.http.HttpRequest;
import model.http.HttpResponse;
import model.http.ViewLocation;
import utils.HttpStatus;

public class ResourceController extends AbstractController {
    private static final String TEMPLATE_FILE_EXTENSION = ".html";
    private static final String RESOURCE_FILE_DELIMITER = ".";

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        response.sendRedirect(ViewLocation.STATIC.getLocation() + request.getPath(), HttpStatus.OK);
    }

    @Override
    public boolean isMapping(HttpRequest request) {
        return request.getPath().contains(RESOURCE_FILE_DELIMITER)
                && !request.getPath().endsWith(TEMPLATE_FILE_EXTENSION);
    }
}
