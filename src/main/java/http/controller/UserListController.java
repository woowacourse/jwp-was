package http.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.common.HttpSession;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    private static final String LOGINED = "logined";

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (isLogined(httpRequest)) {
            renderUserListPage(httpResponse);
            return;
        }
        httpResponse.redirect("/user/login.html");
    }

    private boolean isLogined(HttpRequest httpRequest) {
        HttpSession httpSession = httpRequest.getSession();
        Object logined = httpSession.getAttribute(LOGINED);
        return logined != null && (boolean) logined;
    }

    private void renderUserListPage(HttpResponse httpResponse) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("increment", (context, options) -> (Integer) context + 1);
        try {
            Template template = handlebars.compile("user/list");
            Map<String, Collection<User>> model = new HashMap<>();
            model.put("users", DataBase.findAll());
            httpResponse.ok(template.apply(model).getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage());
            httpResponse.sendInternalServerError();
        }
    }
}
