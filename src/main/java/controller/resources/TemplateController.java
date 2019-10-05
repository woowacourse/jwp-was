package controller.resources;

import controller.Controller;
import controller.annotation.RequestMapping;
import webserver.http.HttpMethod;
import webserver.http.HttpStatus;
import webserver.http.ModelAndView;
import webserver.http.ViewLocation;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class TemplateController implements Controller {
    @RequestMapping(method = HttpMethod.GET, url = "templates")
    public ModelAndView templateResourceRequest(HttpRequest request, HttpResponse response) {
        return new ModelAndView(ViewLocation.TEMPLATE.getLocation() + request.getPath(), HttpStatus.OK);
    }
}
