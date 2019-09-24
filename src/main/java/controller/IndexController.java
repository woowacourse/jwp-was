package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class IndexController extends AbstractController {
    private static class IndexControllerLazyHolder {
        private static final IndexController INSTANCE = new IndexController();
    }

    public static IndexController getInstance() {
        return IndexControllerLazyHolder.INSTANCE;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setResponseStatus(ResponseStatus.OK);
        httpResponse.addHeaderAttribute("Content-Type", "text/html;charset=utf-8");
        try {
            httpResponse.setBody(FileIoUtils.loadFileFromClasspath("./templates" + "/index" + ".html"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
