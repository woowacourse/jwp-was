package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.staticfile.StaticFileMatcher;

public class StaticFileController extends AbstractController {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.forward(StaticFileMatcher.findStaticFilePath(httpRequest.getResourcePath()));
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

    }
}
