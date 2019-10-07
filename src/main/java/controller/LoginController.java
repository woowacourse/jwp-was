package controller;

import db.DataBase;
import db.NotFoundEntityException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.InvalidUserException;
import model.User;
import session.Session;
import view.ModelAndView;

import java.util.Map;

import static http.Cookie.LOGINED;

public class LoginController extends BasicController {
    @Override
    public ModelAndView doPost(HttpRequest request, HttpResponse response) {
        Map<String, String> bodyData = request.convertBodyToMap();
        Session session = request.getSession(response);
        try {
            User user = DataBase.findUserById(bodyData.get("userId"));
            if (user.matchPassword(bodyData.get("password"))) {
                session.setAttribute(LOGINED, true);
                return new ModelAndView("redirect: /index.html");
            }
            return new ModelAndView("redirect: /user/login_failed.html");
        } catch (NotFoundEntityException | InvalidUserException e) {
            session.setAttribute(LOGINED, false);
            return new ModelAndView("redirect: /user/login_failed.html");
        }
    }
}
