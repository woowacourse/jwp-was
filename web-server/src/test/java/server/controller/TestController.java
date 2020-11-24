package server.controller;

import server.web.controller.Controller;
import server.web.controller.RequestMapping;
import server.web.request.HttpMethod;
import server.web.request.HttpRequest;
import server.web.response.HttpResponse;
import server.web.view.ModelAndView;

@RequestMapping(uri = "/test", httpMethod = HttpMethod.GET)
public class TestController implements Controller {

    @Override
    public ModelAndView doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        return null;
    }
}
