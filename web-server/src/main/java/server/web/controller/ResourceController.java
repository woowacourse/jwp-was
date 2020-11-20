package server.web.controller;

import server.web.request.HttpRequest;
import server.web.response.HttpResponse;
import server.web.view.FileView;
import server.web.view.ModelAndView;

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
