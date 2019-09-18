package http.controller;

import http.model.HttpRequest;
import http.model.HttpUri;
import http.view.ModelAndView;
import http.view.View;

public class FileResourceRequestHandler implements HttpRequestHandler {

    @Override
    public ModelAndView handle(HttpRequest httpRequest) {
        return new ModelAndView(getViewByUri(httpRequest.getUri()));
    }

    private View getViewByUri(HttpUri uri) {
        return new View(uri);
    }
}
