package domain.controller;

import application.Controller;
import domain.UrlMapper;
import lombok.RequiredArgsConstructor;
import servlet.HttpRequest;
import servlet.HttpResponse;

@RequiredArgsConstructor
public class FrontController implements Controller {

    private final Controller staticFileController;
    private final UrlMapper urlMapper;

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.hasPathOfStaticFile()) {
            staticFileController.service(httpRequest, httpResponse);
            return;
        }
        if (urlMapper.contains(httpRequest.getPath())) {
            urlMapper.getController(httpRequest.getPath()).service(httpRequest, httpResponse);
            return;
        }
        httpResponse.respondPageNotFound();
    }
}
