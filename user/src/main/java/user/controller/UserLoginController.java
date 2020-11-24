package user.controller;

import static java.util.Objects.*;

import user.db.DataBase;
import user.model.User;
import webserver.controller.Controller;
import webserver.http.request.HttpParams;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.servlet.AbstractView;
import webserver.servlet.RedirectView;

public class UserLoginController implements Controller {
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String LOGINED_FALSE = "logined=false";
    private static final String PATH = "/";
    private static final String LOGINED_TRUE = "logined=true";

    @Override
    public AbstractView doService(HttpRequest request, HttpResponse response) {
        String userId = HttpParams.of(request.getBody()).get(USER_ID);
        String password = HttpParams.of(request.getBody()).get(PASSWORD);
        User user = DataBase.findUserById(userId);

        if (isNull(user) || user.isPasswordNotEquals(password)) {
            response.setCookie(LOGINED_FALSE, PATH);
            return new RedirectView("/user/login_failed.html");
        }

        response.setCookie(LOGINED_TRUE, PATH);
        return new RedirectView("/index.html");
    }
}
