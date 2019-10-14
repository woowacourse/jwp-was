package http.controller;

import db.DataBase;
import http.model.HttpRequest;
import http.model.HttpResponse;
import http.model.HttpSession;
import model.User;

public class LoginController implements Controller {
    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        User user = DataBase.findUserById(userId);

        if (!(user == null) && user.isValidPassword(password)) {
            HttpSession httpSession = httpRequest.getHttpSession();
            httpSession.setAttributes("user", user);

            return new HttpResponse.Builder()
                    .sendRedirect("/index.html")
                    .build();
        }
        return new HttpResponse.Builder()
                .sendRedirect("/user/login_failed.html")
                .build();
    }
}
