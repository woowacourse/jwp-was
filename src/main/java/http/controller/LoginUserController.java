package http.controller;

import db.DataBase;
import db.exception.NotFoundException;
import db.exception.WrongPasswordException;
import http.common.HttpCookie;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginUserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(LoginUserController.class);

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpCookie requestHttpCookie = httpRequest.getHttpCookie();
        HttpCookie responseHttpCookie = httpResponse.getHttpCookie();

        try {
            User user = DataBase.findUserByIdAndPassword(httpRequest.getParameter("userId"), httpRequest.getParameter("password"));
            responseHttpCookie.put("logined", "true");
            httpResponse.redirect("/index.html");
        } catch (NotFoundException | WrongPasswordException e) {
            responseHttpCookie.put("logined", "false");
            httpResponse.redirect("/user/login_failed.html");
        }

        logger.debug("loginUser : {}", httpResponse);
    }
}
