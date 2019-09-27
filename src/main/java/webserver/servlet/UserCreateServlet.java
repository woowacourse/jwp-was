package webserver.servlet;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.view.ModelAndView;
import webserver.view.View;

import java.io.IOException;

public class UserCreateServlet extends RequestServlet {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final String url = "/user/create";

    @Override
    public ModelAndView doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        User user = new User(httpRequest.getBody("userId"), httpRequest.getBody("password"), httpRequest.getBody("name"), httpRequest.getBody("email"));
        logger.debug(">>> User : {}", user);
        DataBase.addUser(user);
        httpResponse.redirect("/");
        return new ModelAndView(null);
    }

    @Override
    protected String getUrl() {
        return url;
    }
}
