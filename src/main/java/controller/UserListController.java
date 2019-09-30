package controller;

import controller.exception.NotSupportMethod;
import http.HttpStatusCode;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserListController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserListController.class);

    public static final String PATH = "/user/list";

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new NotSupportMethod("Not support" + httpRequest.getMethod());
    }

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        LOGGER.debug("userList request get: {}", httpRequest.getUri());
        if (httpRequest.getCookieValue("logined").equals("true")) {
            httpResponse.setStatusCode(HttpStatusCode.OK);
            httpResponse.forward(httpRequest.getPath() + "list.html");
            return;
        }

        httpResponse.setStatusCode(HttpStatusCode.NOT_FOUND);
        httpResponse.forward("/index.html");
    }
}
