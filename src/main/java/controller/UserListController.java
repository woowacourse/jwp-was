package controller;

import com.github.jknack.handlebars.Template;
import config.TemplateFactory;
import db.UserRepository;
import http.request.Request;
import http.response.Response;
import http.session.HttpSession;
import http.session.HttpSessionStore;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.Collection;

public class UserListController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserListController.class);

    @Override
    public void doGet(Request request, Response response) throws IOException {
        try {
            String path = request.getPath();
            Template template = TemplateFactory.of(path);
            Collection<User> users = UserRepository.findAll();

            HttpSession httpSession = request.getSession();
            if (!HttpSessionStore.isContains(httpSession)) {
                throw new AuthenticationException();
            }
            response.ok(template.apply(users));
        } catch (AuthenticationException e) {
            LOGGER.info(e.getMessage());
            response.found("/user/login.html");
        }
    }
}
