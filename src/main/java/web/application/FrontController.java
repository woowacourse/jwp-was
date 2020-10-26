package web.application;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import web.application.controller.Controller;
import web.application.controller.StaticController;
import web.server.domain.request.HttpRequest;
import web.server.domain.response.HttpResponse;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FrontController implements Controller {

    private static final StaticController STATIC_CONTROLLER = new StaticController();

    private final UrlMapper urlMapper;

    public static FrontController from(UrlMapper urlMapper) {
        return new FrontController(urlMapper);
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.hasPathOfStaticFile()) {
            STATIC_CONTROLLER.service(httpRequest, httpResponse);
            return;
        }
        if (urlMapper.contains(httpRequest.getPath())) {
            urlMapper.getController(httpRequest.getPath()).service(httpRequest, httpResponse);
            return;
        }
        httpResponse.respondPageNotFound();
    }
}
