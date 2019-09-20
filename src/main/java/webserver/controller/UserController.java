package webserver.controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import utils.ParameterParser;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

public class UserController extends AbstractController {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        if(request.hasParameters()) {
            DataBase.addUser(createUser(request));
            response.response200Header(0);
        }
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws UnsupportedEncodingException {
        if(request.hasBody()) {
            String body = request.getBody().toString();
            body = URLDecoder.decode(body, "UTF-8");
            Map<String, String> bodyData = ParameterParser.parse(body);

            DataBase.addUser(createUser(bodyData));

            // response 만들기
            response.response302Header("/index.html");

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
