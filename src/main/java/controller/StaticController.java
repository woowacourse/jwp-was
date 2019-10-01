package controller;

import http.HttpRequest;
import http.HttpResponse;
import http.view.StaticViewResolver;
import http.view.View;
import http.view.ViewResolver;

public class StaticController extends AbstractController {
    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        ViewResolver viewResolver = new StaticViewResolver();
        View view = viewResolver.resolveViewName(request.getPath());
        view.render(request, response);
    }
}
