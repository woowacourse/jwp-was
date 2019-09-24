package controller.custom;

import controller.AbstractController;
import model.http.HttpRequest;
import model.http.HttpResponse;
import model.http.ViewLocation;
import utils.HttpStatus;

public class HomeController extends AbstractController {

    private static final String ROOT_URL = "/";
    private static final String INDEX_PAGE = "/index.html";

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        response.sendRedirect(ViewLocation.TEMPLATE.getLocation() + INDEX_PAGE, HttpStatus.OK);
    }

    @Override
    public boolean isMapping(HttpRequest request) {
        return ROOT_URL.equals(request.getPath());
    }
}
