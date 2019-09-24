package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class MainController extends AbstractController {

    @Override
    void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        try {
            httpResponse.forward("/index.html", FileIoUtils.loadFileFromClasspath("./templates/index.html"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
