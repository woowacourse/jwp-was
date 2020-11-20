package domain.controller;

import domain.UrlMapper;
import domain.request.HttpRequest;
import domain.response.HttpResponse;

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
