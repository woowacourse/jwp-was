package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseResolver;
import http.response.view.ModelAndView;
import http.response.view.RedirectView;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import session.HttpSession;
import session.HttpSessionManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static controller.UserLoginController.LOGIN_USER;

public class UserListController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UserListController.class);

    private UserService userService = new UserService();

    @Override
    void doGet(HttpRequest request, HttpResponse response) {
        if (checkLogin(request)) {
            showUsers(response);
            return;
        }
        ResponseResolver.resolve(new RedirectView("/user/login.html"), response);
    }

    private boolean checkLogin(HttpRequest request) {
        HttpSession session = request.getSession(HttpSessionManager.getInstance());
        return session.getAttributes(LOGIN_USER) != null;
    }

    private void showUsers(HttpResponse response) {
        List<User> users = userService.findAll();

        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        try {
            ResponseResolver.resolve(new ModelAndView("/user/list.html", model), response);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
