package controller;

import java.io.IOException;
import java.util.Collection;

import javax.naming.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jknack.handlebars.Template;
import config.TemplateFactory;
import db.DataBase;
import http.request.Request;
import http.response.Response;
import model.User;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    @Override
    public void doGet(Request request, Response response) throws IOException {
        try {
            String path = request.getPath();
            Template template = TemplateFactory.of(path);
            Collection<User> users = DataBase.findAll();
            validate(request);
            response.ok(template.apply(users));
        } catch (AuthenticationException e) {
            logger.info(e.getMessage());
            response.found("/user/login.html");
        }
    }

    private void validate(Request request) throws AuthenticationException {
        if (request.getCookie("logined") == null || !Boolean.parseBoolean(
                request.getCookie("logined"))) {
            throw new AuthenticationException("로그인을 하지 않은 사용자가 접속했습니다.");
        }
    }
}
