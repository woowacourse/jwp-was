package webserver.servlet;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import webserver.domain.request.HttpRequest;

public class UserCreate implements Servlet{
    private static Logger logger = LoggerFactory.getLogger(UserCreate.class);


    @Override
    public void service(HttpRequest httpRequest) {
        if (httpRequest.isGet()) {
            get(httpRequest);
            return;
        }

        if (httpRequest.isPost()) {
            post(httpRequest);
            return;
        }
    }

    @Override
    public void post(HttpRequest httpRequest) {
        Map<String, String> data = new HashMap<>();
        String body = httpRequest.getBody();
        logger.debug("body : {}", body);
        String[] inputs = body.split("&");
        for (String input : inputs) {
            logger.debug("bodyParam : {}", input);
            String[] keyAndValue = input.split("=");
            if (keyAndValue.length < 2) {
                return;
            }
            logger.debug("key : {}", keyAndValue[0]);
            logger.debug("value : {}", keyAndValue[1]);
            data.put(keyAndValue[0], keyAndValue[1]);
        }
        String userId = data.get("userId");
        String password = data.get("password");
        String name = data.get("name");
        String email = data.get("email");

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
    }

    @Override
    public void get(HttpRequest httpRequest) {
        Map<String, String> parameters = httpRequest.getParameters();
        String userId = parameters.get("userId");
        String password = parameters.get("password");
        String name = parameters.get("name");
        String email = parameters.get("email");

        if (userId.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty()) {
            return;
        }

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
    }
}
