package controller.custom;

import controller.Controller;
import controller.annotation.RequestMapping;
import webserver.http.HttpMethod;
import webserver.http.HttpStatus;
import webserver.http.ModelAndView;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class
HomeController implements Controller {
    @RequestMapping(method = HttpMethod.GET, url = "/")
    private ModelAndView rootRequest(HttpRequest request, HttpResponse response) {
        return new ModelAndView("/index.html", HttpStatus.OK);
    }
}