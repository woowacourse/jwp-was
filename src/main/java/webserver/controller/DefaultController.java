package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class DefaultController extends AbstractController {

    private DefaultController() {
    }

    public static DefaultController getInstance() {
        return DefaultControllerLazyHolder.INSTANCE;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse)
        throws IOException, URISyntaxException {
        httpResponse.forward(httpRequest.getPath());
    }

    private static class DefaultControllerLazyHolder {

        private static final DefaultController INSTANCE = new DefaultController();
    }
}
