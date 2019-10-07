package http.application.service;

import db.DataBase;
import http.application.Service;
import http.request.HttpRequest;
import http.request.MessageBody;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUserService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserService.class);

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        MessageBody messageBody = httpRequest.getMessageBody();

        User user = new User(messageBody.get("userId"), messageBody.get("password"),
                messageBody.get("name"), messageBody.get("email"));

        DataBase.addUser(user);
        logger.info("user created: {}", user);
        httpResponse.redirect("/index.html");
    }
}
