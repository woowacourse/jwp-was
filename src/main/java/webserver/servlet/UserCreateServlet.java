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

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        User user = new User(httpRequest.getBody("userId"), httpRequest.getBody("password"), httpRequest.getBody("name"), httpRequest.getBody("email"));
        DataBase.addUser(user);
        logger.debug(">>> User : {}", user);
        logger.debug(">>> Saved User : {}", DataBase.findUserById(user.getUserId()));
        ResponseHeader header = new ResponseHeader();
        header.setLocation("/index.html");
        return HttpResponse.found(header);
    }
}
