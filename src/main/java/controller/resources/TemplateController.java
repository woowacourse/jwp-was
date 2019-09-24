package controller.resources;

import controller.AbstractController;
import model.http.HttpRequest;
import model.http.HttpResponse;
import model.http.ViewLocation;
import utils.HttpStatus;

public class TemplateController extends AbstractController {

    private static final String TEMPLATE_FILE_EXTENSION = ".html";

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        response.sendRedirect(ViewLocation.TEMPLATE.getLocation() + request.getPath(), HttpStatus.OK);
    }

    @Override
    public boolean isMapping(HttpRequest request) {
        return request.getPath().endsWith(TEMPLATE_FILE_EXTENSION);
    }
}
