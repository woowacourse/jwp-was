package controller;

import db.DataBase;
import http.exception.NotFoundCookieException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HandlebarsHelper;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserListController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserListController.class);

    public static final String PATH = "/user/list";

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        LOGGER.debug("userList request get: {}", httpRequest.getUri());
        try {
            if (httpRequest.getCookieValue("logined").equals("true")) {
                httpResponse.forward(httpRequest.getPath() + "list.html");
                httpResponse.setHttpResponseBody(new HttpResponseBody(HandlebarsHelper.apply(DataBase.findAll())));
            }
        } catch (NotFoundCookieException e) {
            httpResponse.redirect("login_failed.html");
        }

    }
}
