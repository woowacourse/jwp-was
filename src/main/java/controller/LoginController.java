package controller;

import db.DataBase;
import db.NotFoundEntityException;
import http.request.Cookie;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.InvalidUserException;
import model.User;
import session.Sessions;
import webserver.resolver.BadRequestException;

import java.util.Map;

public class LoginController extends BasicController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        throw new BadRequestException();
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        Map<String, String> bodyData = request.convertBodyToMap();
        Cookie cookie = new Cookie();

        try {
            User user = DataBase.findUserById(bodyData.get("userId"));
            if (user.equalPassword(bodyData.get("password"))) {
                String jSessionId = Sessions.create();
                cookie.addCookie("JSESSIONID", jSessionId);
                cookie.addCookie("logined", "true");

                response.redirectResponseWithCookie(cookie, "/index.html");
            }
        } catch (NotFoundEntityException | InvalidUserException e) {
            cookie.addCookie("logined", "false");

            response.redirectResponseWithCookie(cookie, "/user/login_failed.html");
        }
    }
}
