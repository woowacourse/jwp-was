package webserver.servlet;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.ResponseHeader;

public class UserCreateServlet extends RequestServlet {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String REQUEST_PARAMS_USER_ID = "userId";
    private static final String REQUEST_PARAMS_USER_PASSWORD = "password";
    private static final String REQUEST_PARAMS_USER_NAME = "name";
    private static final String REQUEST_PARAMS_USER_EMAIL = "email";
    private static final String VIEW_CREATE_SUCCESS = "/index.html";

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        User user = new User(httpRequest.getBody(REQUEST_PARAMS_USER_ID), httpRequest.getBody(REQUEST_PARAMS_USER_PASSWORD), httpRequest.getBody(REQUEST_PARAMS_USER_NAME), httpRequest.getBody(REQUEST_PARAMS_USER_EMAIL));
        DataBase.addUser(user);
        logger.debug(">>> User : {}", user);
        logger.debug(">>> Saved User : {}", DataBase.findUserById(user.getUserId()));
        ResponseHeader header = new ResponseHeader();
        header.setLocation(VIEW_CREATE_SUCCESS);
        return HttpResponse.found(header);
    }
}
