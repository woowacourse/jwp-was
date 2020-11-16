package user.service.controller;

import java.io.IOException;
import java.util.Collection;

import javax.naming.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jknack.handlebars.Template;
import http.was.controller.annotation.Controller;
import http.was.controller.annotation.RequestMapping;
import http.was.exception.HttpRequestMethodNotSupportedException;
import http.was.http.HttpMethod;
import http.was.http.request.Request;
import http.was.http.response.Response;
import http.was.http.session.HttpSession;
import http.was.utils.TemplateFactory;
import user.service.db.DataBase;
import user.service.model.User;

@Controller
public class UserListController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    @RequestMapping(path = "/user/list")
    public void doGet(Request request, Response response) throws IOException {
        try {
            String path = request.getPath();
            Template template = TemplateFactory.of(path);
            Collection<User> users = DataBase.findAll();
            HttpSession httpSession = request.getHttpSession(false);

            if (httpSession.getAttribute("email") == null) {
                throw new AuthenticationException();
            }

            response.ok(template.apply(users));
        } catch (AuthenticationException e) {
            logger.info(e.getMessage());
            response.found("/user/login.html");
        }
    }

    @RequestMapping(path = "/user/list", method = HttpMethod.POST)
    public void doPost(Request request, Response response) {
        throw new HttpRequestMethodNotSupportedException(HttpMethod.GET);
    }
}
