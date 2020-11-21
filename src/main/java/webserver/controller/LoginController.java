package webserver.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.UserDataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class LoginController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Override
    protected HttpResponse post(HttpRequest httpRequest) {
        Map<String, String> data = new HashMap<>();
        String body = httpRequest.getBody();
        logger.debug("body : {}", body);
        String[] inputs = body.split("&");
        for (String input : inputs) {
            logger.debug("bodyParam : {}", input);
            String[] keyAndValue = input.split("=");
            if (keyAndValue.length < 2) {
                continue;
            }
            logger.debug("key : {}", keyAndValue[0]);
            logger.debug("value : {}", keyAndValue[1]);
            data.put(keyAndValue[0], keyAndValue[1]);
        }

        String userId = data.get("userId");
        String password = data.get("password");

        User user = UserDataBase.findUserById(userId);

        if (user != null && password.equals(user.getPassword())) {
            return HttpResponse.ok()
                .bodyByPath("./templates/index.html")
                .setCookie("logined", "true", "/")
                .build();
        }

        return HttpResponse.ok()
            .bodyByPath("./templates/user/login_failed.html")
            .setCookie("logined", "false", "/")
            .build();
    }
}
