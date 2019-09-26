package http.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.common.Cookie;
import http.common.HttpSession;
import http.common.HttpStatus;
import http.exception.InvalidHeaderException;
import http.exception.NotExistSessionValue;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.SessionHandler;

import java.io.IOException;
import java.util.ArrayList;

import static http.common.Cookie.LOGINED;
import static http.common.Cookie.LOGINED_TRUE;

public class UserListController extends AbstractController {
    public static final String URL = "/user/list";
    private static final Logger log = LoggerFactory.getLogger(UserListController.class);
    private static final String SESSION_ID = "sessionId";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        try {
            Cookie cookie = request.getCookie(SESSION_ID);
            String sessionId = cookie.getValue();
            HttpSession session = SessionHandler.getInstance().getSession(sessionId);
            Cookie loginedCookie = (Cookie) session.getAttribute(LOGINED);
            if (loginedCookie.getValue().equals(LOGINED_TRUE)) {
                String profilePage = renderingPage();
                response.forward(profilePage.getBytes());
            }
        } catch (InvalidHeaderException | NotExistSessionValue e) {
            log.error(e.getMessage());
            setNotLoginedResponse(response);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        response.forwardErrorPage(HttpStatus.NOT_FOUND);
    }

    private void setNotLoginedResponse(HttpResponse response) {
        response.sendRedirect("/user/login.html");
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
