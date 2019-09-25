package controller;

import db.DataBase;
import db.NotFoundEntityException;
import http.request.HttpRequest;
import http.request.HttpVersion;
import http.response.HttpResponse;
import model.InvalidUserException;
import model.User;
import webserver.resolver.BadRequestException;

import java.util.Map;
import java.util.Optional;

public class LoginController extends BasicController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        throw new BadRequestException();
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        HttpVersion version = request.getVersion();
        Map<String, String> bodyData = request.convertBodyToMap();
        try {
            User user = Optional.ofNullable(DataBase.findUserById(bodyData.get("userId")))
                    .orElseThrow(NotFoundEntityException::new);
            user.checkPassword(bodyData.get("password"));
            response.redirectResponseWithCookie(version, true, "/index.html");
        } catch (NotFoundEntityException | InvalidUserException e) {
            response.redirectResponseWithCookie(version, false, "/user/login_failed.html");
        }
    }
}
