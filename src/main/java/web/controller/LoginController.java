package web.controller;

import db.DataBase;
import model.User;
import web.http.HttpRequest;
import web.http.HttpResponse;
import web.http.HttpStatus;
import web.session.Cookie;
import web.session.Session;
import web.session.SessionFactory;
import web.session.SessionStorage;

public class LoginController extends AbstractController {
    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        try {
            validateUser(request);
            response.redirect("/index.html");
        } catch (IllegalArgumentException e) {
            response.redirect("/user/login_failed.html");
        }

        Session session = SessionFactory.getNewSession();
        SessionStorage.add(session);
        Cookie cookie = new Cookie();
        cookie.add("logined", session.getId());
        response.addCookie(cookie);

    }

    private void validateUser(HttpRequest request) {
        User user = DataBase.findUserById(
            request.getRequestBody().getParameter("userId")
        ).orElseThrow(IllegalArgumentException::new);

        if (user.passwordWrong(request.getRequestBody().getParameter("password"))) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        response.addHttpStatus(HttpStatus.NOT_ALLOWED_METHOD);
        ExceptionHandler.processException(new NoSuchMethodException("지원하지 않는 메서드입니다."), response);
    }
}
