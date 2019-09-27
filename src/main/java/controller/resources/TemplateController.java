package controller.resources;

import annotation.RequestMapping;
import controller.Controller;
import model.http.HttpRequest;
import model.http.HttpResponse;
import model.http.ViewLocation;
import utils.HttpMethod;
import utils.HttpStatus;

public class TemplateController implements Controller {
    @RequestMapping(method = HttpMethod.GET, url = "templates")
    public void templateResourceRequest(HttpRequest request, HttpResponse response) {
        response.sendRedirect(ViewLocation.TEMPLATE.getLocation() + request.getPath(), HttpStatus.OK);
    }
}
