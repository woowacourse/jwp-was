package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UserController extends BasicController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Override
    public HttpResponse doGet(HttpRequest request, HttpResponse response) {
        if (request.hasParameters()) {
            DataBase.addUser(createUser(request));
            response.redirectResponse("/index.html");
        }
        return response;
    }

    @Override
    public HttpResponse doPost(HttpRequest request, HttpResponse response) {
        log.debug("{}", request.hasBody());

        if (request.hasBody()) {
            DataBase.addUser(createUser(request.convertBodyToMap()));
//            response.addHeader(Arrays.asList("Location: /index.html\r\n"));
            response.redirectResponse("/index.html");
        }

        return response;
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
