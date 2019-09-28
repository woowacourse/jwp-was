package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class IndexController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    private static class IndexControllerLazyHolder {
        private static final IndexController INSTANCE = new IndexController();
    }

    public static IndexController getInstance() {
        return IndexControllerLazyHolder.INSTANCE;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            httpResponse.setBody(FileIoUtils.loadFileFromClasspath("./templates" + "/index" + ".html"));
            httpResponse.setResponseStatus(ResponseStatus.OK);
            httpResponse.addHeaderAttribute("Content-Type", "text/html;charset=utf-8");
        } catch (IOException | URISyntaxException e) {
            log.error(e.getMessage(), e.getCause());
        }
    }
}
