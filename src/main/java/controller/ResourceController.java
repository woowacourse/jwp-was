package controller;

import http.request.HttpRequest;
import http.request.HttpRequestMapping;
import view.ModelAndView;
import view.View;

public class ResourceController extends HttpRequestMappingAbstractController {

    public ResourceController(HttpRequestMapping httpRequestMapping) {
        super(httpRequestMapping);
    }

    @Override
    public ModelAndView handle(HttpRequest httpRequest) {
        return ModelAndView.of(new View(httpRequest.getRequestLine().getPath()));
    }
}
