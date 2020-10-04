package web.application;

import java.util.HashMap;
import java.util.Map;

import web.application.controller.Controller;
import web.application.controller.CreateUserController;
import web.application.controller.RootController;
import web.application.controller.StaticController;
import web.server.domain.request.HttpRequest;
import web.server.domain.response.HttpResponse;

public class FrontController implements Controller {

    private final UrlMapper urlMapper;

    public FrontController(UrlMapper urlMapper) {
        this.urlMapper = urlMapper;
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.hasPathOfStaticFile()) {
            StaticController.getInstance().service(httpRequest, httpResponse);
            return;
        }
        if (urlMapper.contains(httpRequest.getPath())) {
            urlMapper.getController(httpRequest.getPath()).service(httpRequest, httpResponse);
            return;
        }
        httpResponse.respondPageNotFound();
    }
}
