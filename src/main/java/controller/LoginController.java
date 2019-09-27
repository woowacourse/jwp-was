package controller;

import db.DataBase;
import db.NotFoundEntityException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.InvalidUserException;
import model.User;
import session.Session;
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
        Session session = Sessions.create();
        try {
            User user = DataBase.findUserById(bodyData.get("userId"));
            if (user.matchPassword(bodyData.get("password"))) {
                session.setAttribute("logined", true);

                response.addCookie("JSESSIONID", session.getId());
                response.addCookie("logined", "true");

                response.redirect("/index.html");
            }
        } catch (NotFoundEntityException | InvalidUserException e) {
            session.setAttribute("logined", false);
            response.addCookie("logined", "false");

            response.redirect("/user/login_failed.html");
        }
    }
}
