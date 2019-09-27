package controller.custom;

import annotation.RequestMapping;
import controller.Controller;
import model.http.HttpRequest;
import model.http.HttpResponse;
import model.http.ViewLocation;
import utils.HttpMethod;
import utils.HttpStatus;

public class HomeController implements Controller {
    private static final String INDEX_PAGE = "/index.html";

    @RequestMapping(method = HttpMethod.GET, url = "/")
    private void rootRequest(HttpRequest request, HttpResponse response) {
        response.sendRedirect(ViewLocation.TEMPLATE.getLocation() + INDEX_PAGE, HttpStatus.OK);
    }
}
