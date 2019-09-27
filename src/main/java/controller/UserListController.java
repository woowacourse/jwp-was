package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.HttpSession;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static controller.LoginController.LOGIN_FALSE;
import static controller.LoginController.LOGIN_KEY;
import static controller.LoginController.LOGIN_TRUE;

public class UserListController extends Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    public static final String PATH = "/user/list";

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpSession httpSession = httpRequest.getHttpSession();
        String logined = (String) httpSession.getAttribute(LOGIN_KEY);

        if (LOGIN_TRUE.equals(logined)) {
            try {
                String htmlBody = makeBody();
                httpResponse.setStatusCode(HttpStatus.OK);
                httpResponse.setBody(htmlBody);
                return;
            } catch (IOException e) {
                httpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                logger.error(e.getMessage());
                return;
            }
        }
        httpResponse.setStatusCode(HttpStatus.FOUND);
        httpSession.setAttribute(LOGIN_KEY, LOGIN_FALSE);
        httpResponse.redirect("/user/login.html");
    }

    private String makeBody() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();

        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/list");
        Map<String, Collection<User>> users = new HashMap<>();
        users.put("users", DataBase.findAll());

        return template.apply(users);
    }
}
