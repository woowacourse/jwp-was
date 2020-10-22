package controller;

import http.AbstractServlet;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.db.DataBase;
import model.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class UserController extends AbstractServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String PATH = "/user";

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(
                decode(httpRequest.getParameter("userId")),
                decode(httpRequest.getParameter("password")),
                decode(httpRequest.getParameter("name")),
                decode(httpRequest.getParameter("email"))
        );
        DataBase.addUser(user);
        logger.debug("save userId: {}, name: {}, email: {}", user.getUserId(), user.getName(), user.getEmail());
        httpResponse.sendRedirect("/index.html");
    }

    private String decode(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException("UnsupportedEncodingException: " + value);
        }
    }

    @Override
    protected String getPath() {
        return PATH;
    }
}
