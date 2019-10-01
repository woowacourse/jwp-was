package http.controller;

import db.DataBase;
import db.exception.NotFoundException;
import db.exception.WrongPasswordException;
import http.common.HttpSession;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginUserController extends DefaultController {
    private static final Logger logger = LoggerFactory.getLogger(LoginUserController.class);

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            User user = DataBase.findUserByIdAndPassword(httpRequest.getParameter("userId"), httpRequest.getParameter("password"));
            HttpSession session = httpRequest.getSession();
            session.setAttribute("user", user);

            httpResponse.redirect("/index.html");
        } catch (NotFoundException | WrongPasswordException e) {
            httpResponse.redirect("/user/login_failed.html");
        }

        logger.debug("loginUser : {}", httpResponse);
    }
}
