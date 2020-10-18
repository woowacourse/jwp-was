package controller;

import com.github.jknack.handlebars.Template;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.servlet.HttpSession;
import http.servlet.SessionContainer;
import model.db.DataBase;
import model.dto.UsersDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import utils.TemplateMaker;

import java.io.IOException;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);
    private static final String PATH = "/user/list";

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            if (isNotLoginUser(httpRequest)) {
                httpResponse.sendRedirect("/user/login.html");
                return;
            }
            Template template = TemplateMaker.handlebarsCompile(httpRequest.getPath());
            UsersDto usersDto = new UsersDto(DataBase.findAll());
            String body = template.apply(usersDto);
            httpResponse.forward(httpRequest, body.getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private boolean isNotLoginUser(HttpRequest httpRequest) {
        String sessionId = httpRequest.getCookie(SessionContainer.SESSION_KEY_FOR_COOKIE);
        HttpSession session = SessionContainer.getInstance().getSession(sessionId);
        String logined = session.getAttribute(LoginController.SESSION_KEY_OF_LOGIN);
        return StringUtils.isEmpty(logined)
                || logined.equals(Boolean.toString(false));
    }

    @Override
    protected String getPath() {
        return PATH;
    }
}
