package http.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.common.Cookie;
import http.exception.InvalidHeaderException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

public class UserListController extends AbstractController {
    public static final String URL = "/user/list";
    private static final Logger log = LoggerFactory.getLogger(UserListController.class);
    private static final String LOGIN_COOKIE_KEY = "logined";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        try {
            Cookie cookie = request.getCookie(LOGIN_COOKIE_KEY);
            if (cookie.getValue().equals("false")) {
                setNotLoginedResponse(response);
                return;
            }

            String profilePage = renderingPage();
            response.forward(profilePage.getBytes());
        } catch (InvalidHeaderException e) {
            log.error(e.getMessage());
            setNotLoginedResponse(response);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        //Todo
    }

    private void setNotLoginedResponse(HttpResponse response) {
        response.sendRedirect("/user/login.html");
        response.addCookie("logined", "false");
    }

    private String renderingPage() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();

        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/list");
        Users users = new Users(new ArrayList<>(DataBase.findAll()));
        return template.apply(users);
    }
}
