package dev.luffy.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import dev.luffy.http.request.HttpRequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import dev.luffy.annotation.Controller;
import dev.luffy.annotation.RequestMapping;
import dev.luffy.db.DataBase;
import dev.luffy.http.request.HttpRequest;
import dev.luffy.http.response.HttpResponse;
import dev.luffy.model.User;
import dev.luffy.model.UserCollection;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final String FALSE = "false";
    private static final String TRUE = "true";
    private static final String SET_COOKIE = "Set-Cookie";
    private static final String LOGINED_COOKIE_FORMAT = "logined=%s; Path=/";
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    @RequestMapping(
            path = "/user/form.html",
            method = HttpRequestMethod.GET
    )
    public static void form(HttpRequest request, HttpResponse response) {
        logger.debug("request : {}", request);

        response.ok(request);
    }

    @RequestMapping(
            path = "/user/login.html",
            method = HttpRequestMethod.GET
    )
    public static void login(HttpRequest request, HttpResponse response) {
        logger.debug("request : {}", request);

        response.ok(request);
    }

    @RequestMapping(
            path = "/user/login_failed.html",
            method = HttpRequestMethod.GET
    )
    public static void loginFailed(HttpRequest request, HttpResponse response) {
        logger.debug("request : {}", request);

        response.ok(request);
    }

    @RequestMapping(
            path = "/user/list.html",
            method = HttpRequestMethod.GET
    )
    public static void list(HttpRequest request, HttpResponse response) throws IOException {
        logger.debug("request : {}", request);

        if (!request.isLoggedIn()) {
            response.redirect(request, "/index.html");
            return;
        }

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("index", (value, option) -> {
            Integer intValue = (Integer) value;
            return intValue + 1;
        });
        Template template = handlebars.compile("user/list");
        response.ok(request, template.apply(new UserCollection(DataBase.findAll())));
    }

    @RequestMapping(
            path = "/user/create",
            method = HttpRequestMethod.POST
    )
    public static void createUser(HttpRequest request, HttpResponse response) {
        logger.debug("request : {}", request);

        User user = new User(
                request.getBodyParameter(USER_ID),
                request.getBodyParameter(PASSWORD),
                request.getBodyParameter(NAME),
                request.getBodyParameter(EMAIL)
        );

        DataBase.addUser(user);

        response.redirect(request, "/index.html");
    }

    @RequestMapping(
            path = "/user/login",
            method = HttpRequestMethod.POST
    )
    public static void loginUser(HttpRequest request, HttpResponse response) {
        String userId = request.getBodyParameter("userId");
        User loginUser = DataBase.findUserById(userId);

        logger.debug("login User : {}", loginUser);

        String logined = FALSE;

        if (loginUser.matchPassword(request.getBodyParameter("password"))) {
            logined = TRUE;
        }

        response.addHeader(SET_COOKIE, String.format(LOGINED_COOKIE_FORMAT, logined));
        response.redirect(request, "/index.html");
    }

    @RequestMapping(
            path = "/user/list",
            method = HttpRequestMethod.GET
    )
    public static void userList(HttpRequest request, HttpResponse response) {
        logger.debug("request : {}", request);

        if (request.isLoggedIn()) {
            response.redirect(request, "/user/list.html");
        } else {
            response.redirect(request, "/user/login.html");
        }
    }
}
