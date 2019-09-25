package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import utils.FileIoUtils;

public class IndexController extends AbstractController {
    private static class IndexControllerLazyHolder {
        private static final IndexController INSTANCE = new IndexController();
    }

    public static IndexController getInstance() {
        return IndexControllerLazyHolder.INSTANCE;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setBody(FileIoUtils.loadFileFromClasspath("./templates" + "/index" + ".html"));
        httpResponse.setResponseStatus(ResponseStatus.OK);
        httpResponse.addHeaderAttribute("Content-Type", "text/html;charset=utf-8");
    }
}
