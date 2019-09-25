package controller;

import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserListController extends Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    public static final String PATH = "/user/list";

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if ("true".equals(httpRequest.getCookie("logined"))) {
            try {
                httpResponse.setStatusCode(HttpStatus.OK);
                httpResponse.forward("/user/list.html");
                return;
            } catch (IOException | URISyntaxException e) {
                httpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                logger.error(e.getMessage());
            }
        }
        httpResponse.setStatusCode(HttpStatus.FOUND);
        httpResponse.redirect("/user/login.html");
    }
}
