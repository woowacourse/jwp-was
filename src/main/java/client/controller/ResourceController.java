package client.controller;

import web.controller.Controller;
import web.request.HttpRequest;
import web.response.HttpResponse;
import web.view.FileView;
import web.view.ModelAndView;

public class ResourceController implements Controller {
    private final String filePath;

    public ResourceController(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public ModelAndView doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        return ModelAndView.view(new FileView(filePath));
    }
}
