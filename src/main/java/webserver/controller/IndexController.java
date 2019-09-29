package webserver.controller;

import utils.FileIoUtils;
import webserver.ModelAndView;
import webserver.controller.request.HttpRequest;
import webserver.controller.response.HttpResponse;

import java.io.IOException;
import java.util.Optional;

public class IndexController extends AbstractController {
    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.applyTemplateEngine(httpRequest.getPath());
        return HttpResponse.ok(httpRequest,modelAndView.getView());
    }
}
