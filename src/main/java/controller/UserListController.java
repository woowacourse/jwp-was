package controller;

import com.github.jknack.handlebars.Template;
import db.DataBase;
import exception.RequestParameterNotFoundException;
import model.User;
import utils.TemplateUtils;
import web.request.HttpRequest;
import web.response.HttpResponse;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

public class UserListController extends AbstractController {
    public static final String LOGIN_HTML_PATH = "/user/login.html";

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        try {
            validate(request);
            String path = request.getTarget();
            Template template = TemplateUtils.buildTemplate(path);
            Collection<User> users = DataBase.findAll();
            String result = template.apply(users);
            response.ok(result);
        } catch (IllegalAccessException | RequestParameterNotFoundException | IOException e) {
            logger.error(e.getMessage());
            response.found(LOGIN_HTML_PATH);
        }
    }

    private void validate(HttpRequest request) throws IllegalAccessException {
        String cookie = request.getCookieByKey("logined");
        if (Objects.isNull(cookie) || cookie.isEmpty() || !Boolean.parseBoolean(cookie)) {
            throw new IllegalAccessException("로그인하지 않은 사용자입니다.");
        }
    }
}
