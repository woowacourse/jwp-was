package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.template.TemplateEngineCompileException;
import http.response.view.ModelAndView;
import http.response.view.RedirectView;
import http.response.view.View;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import session.HttpSession;
import session.InMemoryHttpSessionContainer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserListController extends AbstractController {
    private static final String LOGIN_USER = "login-user";
    private static final Logger log = LoggerFactory.getLogger(UserListController.class);
    private static final String USERS = "users";

    private UserService userService = new UserService();

    @Override
    View doGet(HttpRequest request, HttpResponse response) {
        if (checkLogin(request)) {
            return doModelInsertion(response);
        }
        return new RedirectView("/user/login.html");
    }

    private boolean checkLogin(HttpRequest request) {
        HttpSession session = request.getSession(InMemoryHttpSessionContainer.getInstance());
        return session != null && session.getAttributes(LOGIN_USER) != null;
    }

    private View doModelInsertion(HttpResponse response) {
        List<User> users = userService.findAll();

        Map<String, Object> model = new HashMap<>();
        model.put(USERS, users);

        try {
            return new ModelAndView("/user/list.html", model);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new TemplateEngineCompileException("템플릿 컴파일 중 문제 발생");
        }
    }
}
