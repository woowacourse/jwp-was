package webserver.controller;

import db.DataBase;
import http.Cookie;
import http.Session;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogoutController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(LogoutController.class);

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        log.debug("called..!!");

        // to test session.invalidate();
        // suppose only loggedIn user can access here

        Session session = request.getSession(false).get();
        session.invalidate();

        // to index page
        String redirectLocation = "/index.html";
        response.setHeader("Location", redirectLocation);
        response.response302Header();
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        log.debug("called..!!");

//        if (request.hasParameters()) {
//            String userId = request.getParameter("userId");
//            String password = request.getParameter("password");
//
//            User user = DataBase.findUserById(userId);
//
//            String redirectLocation = "/index.html";
//            if (user == null || !user.getPassword().equals(password)) {
//                redirectLocation = "/user/login_failed.html";
//                response.setHeader("Location", redirectLocation);
//                response.response302Header();
//                return;
//            }
//
//            Session session = request.getSession(true).get();
//            session.setAttribute("user", user);
//
//            Cookie cookie = Cookie.create();
//            cookie.add("JWP_WAS_SESSION_ID", session.getId());
//
//            response.setHeader("Set-Cookie", cookie.toHeaderValue());
//            response.setHeader("Location", redirectLocation);
//            response.response302Header();
//        }
    }
}
