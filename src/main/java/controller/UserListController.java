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
import http.session.HttpSession;
import model.User;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    @Override
    public void doGet(Request request, Response response) throws IOException {
        try {
            String path = request.getPath();
            Template template = TemplateFactory.of(path);
            Collection<User> users = DataBase.findAll();
            HttpSession httpSession = request.getHttpSession();
        
            if (httpSession.getAttribute("email") == null) {
                throw new AuthenticationException();
            }

            response.ok(template.apply(users));
        } catch (AuthenticationException e) {
            logger.info(e.getMessage());
            response.found("/user/login.html");
        }
    }
}
