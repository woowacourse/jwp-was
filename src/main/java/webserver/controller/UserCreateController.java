package webserver.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class UserCreateController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);

    @Override
    public HttpResponse post(HttpRequest httpRequest) {
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
        joinMember(data);
        return HttpResponse.found("/index.html").build();
    }

    @Override
    public HttpResponse get(HttpRequest httpRequest) {
        Map<String, String> parameters = httpRequest.getParameters();
        joinMember(parameters);
        return HttpResponse.found("/index.html").build();
    }

    private void joinMember(Map<String, String> data) {
        String userId = data.get("userId");
        String password = data.get("password");
        String name = data.get("name");
        String email = data.get("email");

        if (userId.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty()) {
            throw new ApplicationBusinessException("회원 가입이 실패하였습니다. 입력하지 않은 정보가 있습니다.");
        }

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
    }
}
