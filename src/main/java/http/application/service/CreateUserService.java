package http.application.service;

import db.DataBase;
import http.application.Service;
import http.request.HttpRequest;
import http.request.MessageBodyParser;
import http.request.bodyparser.FormDataParser;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CreateUserService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserService.class);

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        MessageBodyParser bodyParser = new FormDataParser();

        Map<String, String> formData = bodyParser.parse(httpRequest.getHttpRequestBody());

        User user = new User(formData.get("userId"), formData.get("password"),
                formData.get("name"), formData.get("email"));

        DataBase.addUser(user);
        logger.info("user created: {}", user);
        httpResponse.redirect("/index.html");
    }
}
