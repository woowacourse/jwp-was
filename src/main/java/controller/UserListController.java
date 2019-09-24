package controller;

import http.HTTP;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.view.ModelAndView;
import http.response.view.RedirectView;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import session.HttpSession;
import session.SessionDB;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UserListController.class);

    private UserService userService = new UserService();

    @Override
    void doGet(HttpRequest request, HttpResponse response) {
        HttpSession session = SessionDB.getSession(request.getHeaderContents(HTTP.COOKIE));
        if (session != null && session.getAttributes("login-user") != null) {
            showUsers(response);
            return;
        }
        response.render(new RedirectView("/user/login.html"));
    }

    private void showUsers(HttpResponse response) {
        List<User> users = userService.findAll();

        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        try {
            response.render(new ModelAndView("/user/list.html", model));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    void doPost(HttpRequest request, HttpResponse response) {

    }
}
