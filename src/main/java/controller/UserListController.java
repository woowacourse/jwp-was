package controller;

import com.github.jknack.handlebars.Template;
import db.DataBase;
import exception.RequestParameterNotFoundException;
import model.User;
import utils.TemplateUtils;
import web.request.HttpRequest;
import web.response.HttpResponse;
import web.session.SessionStore;

import java.io.IOException;
import java.util.Collection;

public class UserListController extends AbstractController {
    public static final String LOGIN_HTML_PATH = "/user/login.html";

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        try {
            String sessionId = request.getSession();
            if (!SessionStore.isContains(sessionId)) {
                throw new IllegalAccessException("세션을 확인할 수 없습니다.");
            }
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
}
