package web.controller;

import domain.db.DataBase;
import domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.storage.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static webserver.response.MediaType.TEXT_HTML_VALUE;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public static void goForm(HttpRequest request, HttpResponse response) {
        response.setContentType(TEXT_HTML_VALUE);
        response.forward("/user/form.html");
    }

    public static void createUser(HttpRequest request, HttpResponse response) {
        User user = new User(
                request.getBody("userId"),
                request.getBody("password"),
                request.getBody("name"),
                request.getBody("email")
        );
        logger.debug("user : {}", user);
        DataBase.addUser(user);

        response.sendRedirect("/");
    }

    public static void goLoginForm(HttpRequest request, HttpResponse response) {
        response.setContentType(TEXT_HTML_VALUE);
        response.forward("/user/login.html");
    }

    public static void goLoginFail(HttpRequest request, HttpResponse response) {
        response.setContentType(TEXT_HTML_VALUE);
        response.forward("/user/login_failed.html");
    }

    public static void login(HttpRequest request, HttpResponse response) {
        String userId = request.getBody("userId");
        String password = request.getBody("password");

        User user = DataBase.findUserById(userId);

        if (user.notMatchPassword(password)) {
            response.sendRedirect("/login-fail");
            return;
        }

        HttpSession httpSession = request.getSession();
        response.addHeader("Set-Cookie", "JSESSIONID=" + httpSession.getId() + "; Path=/");
        httpSession.setAttribute("user", user);
        response.sendRedirect("/");
    }

    public static void goUserList(HttpRequest request, HttpResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("/login");
            return;
        }

        Map<String, Object> model = new HashMap<>();
        model.put("users", DataBase.findAll());

        response.setContentType(TEXT_HTML_VALUE);
        response.templateForward("user/list.html", model);
    }
}
