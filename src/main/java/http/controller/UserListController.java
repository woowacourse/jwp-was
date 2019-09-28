package http.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.common.Cookie;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        List<Cookie> cookies = httpRequest.getCookies();
        Optional<Cookie> loginedCookie = cookies.stream()
                .filter(cookie -> cookie.getName().equals("logined"))
                .findFirst();
        if (loginedCookie.isPresent() && loginedCookie.get().getValue().equals("true")) {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);
            Template template;
            try {
                template = handlebars.compile("user/list");
            } catch (IOException e) {
                logger.error(e.getMessage());
                httpResponse.sendNotFound();
                return;
            }
            Map<String, Collection<User>> params = new HashMap<>();
            Collection<User> users = DataBase.findAll();
            params.put("users", users);
            try {
                String userListPage = template.apply(params);
                httpResponse.ok(userListPage.getBytes());
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            return;
        }
        httpResponse.redirect("/user/login.html");
    }
}
