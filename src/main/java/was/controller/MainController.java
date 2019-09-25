package was.controller;

import utils.FileIoUtils;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class MainController extends AbstractController {
    private MainController() {}

    public static MainController getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static class LazyHolder {
        private static final MainController INSTANCE = new MainController();
    }

    @Override
    void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        try {
            httpResponse.forward("/index.html", FileIoUtils.loadFileFromClasspath("./templates/index.html"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
