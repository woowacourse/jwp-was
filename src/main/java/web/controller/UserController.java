package web.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import domain.db.DataBase;
import domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.storage.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public static void goForm(HttpRequest request, HttpResponse response) {
        response.setContentType("text/html");
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
        response.setContentType("text/html");
        response.forward("/user/login.html");

    }

    public static void goLoginFail(HttpRequest request, HttpResponse response) {
        response.setContentType("text/html");
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

        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);
            handlebars.registerHelper("plusOne", (context, options) -> (Integer) context + 1);

            Template template = handlebars.compile("user/list");
            String aa = template.apply(model);
            response.templateForward(aa);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
