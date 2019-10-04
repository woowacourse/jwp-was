package domain.controller;

import mvc.annotation.RequestMapping;
import mvc.controller.AbstractController;
import mvc.view.ForwardView;
import mvc.view.View;
import server.http.request.HttpRequest;

@RequestMapping(urlPath = "/")
public class IndexController extends AbstractController {
    @Override
    public View get(HttpRequest httpRequest) {
        return new ForwardView("index.html");
    }
}
