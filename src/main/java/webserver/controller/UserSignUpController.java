package webserver.controller;

import db.DataBase;
import http.parameter.ParameterParser;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

public class UserSignUpController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UserSignUpController.class);

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        log.debug("begin");
        if (request.hasBody()) {
            String body = request.getBody().toString();
            try {
                body = URLDecoder.decode(body, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error("error: {}", e);
                throw new RuntimeException(e);
            }
//            Map<String, String> bodyData = ParameterParser.parse(body);

//            DataBase.addUser(createUser(bodyData));
            User user = createUser(request);
            log.debug("user: {}", user);
            DataBase.addUser(user);

            // response 만들기

            response.setHeader("Location", "/index.html");
            response.response302Header();
        }
    }

    private User createUser(HttpRequest request) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        return new User(userId, password, name, email);
    }

    private User createUser(Map<String, String> bodyData) {
        String userId = bodyData.get("userId");
        String password = bodyData.get("password");
        String name = bodyData.get("name");
        String email = bodyData.get("email");

        return new User(userId, password, name, email);
    }
}
