package controller;

import db.DataBase;
import db.NotFoundEntityException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.InvalidUserException;
import model.User;
import session.Session;

import java.util.Map;

import static http.Cookie.JSESSIONID;
import static http.Cookie.LOGINED;

public class LoginController extends BasicController {

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        Map<String, String> bodyData = request.convertBodyToMap();
        Session session = request.getSession();
        try {
            User user = DataBase.findUserById(bodyData.get("userId"));
            if (user.matchPassword(bodyData.get("password"))) {
                session.setAttribute(LOGINED, true);

                response.addCookie(JSESSIONID, session.getId());

                response.redirect("/index.html");
            }
        } catch (NotFoundEntityException | InvalidUserException e) {
            session.setAttribute(LOGINED, false);

            response.redirect("/user/login_failed.html");
        }
    }
}
