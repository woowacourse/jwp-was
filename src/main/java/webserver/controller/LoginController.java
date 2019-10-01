package webserver.controller;

import db.DataBase;
import http.Cookie;
import http.Session;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class LoginController extends AbstractController {

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        if (request.hasParameters()) {
            String userId = request.getParameter("userId");
            String password = request.getParameter("password");

            User user = DataBase.findUserById(userId);

            String redirectLocation = "/index.html";
            if (user == null || !user.getPassword().equals(password)) {
                redirectLocation = "/user/login_failed.html";
                response.setHeader("Location", redirectLocation);
                response.response302Header();
                return;
            }

            Session session = request.getSession(true).get();
            session.setAttribute("user", user);

            Cookie cookie = Cookie.create();
            cookie.add("JWP_WAS_SESSION_ID", session.getId());

            response.setHeader("Set-Cookie", cookie.toHeaderValue());
            response.setHeader("Location", redirectLocation);
            response.response302Header();
        }
    }
}
