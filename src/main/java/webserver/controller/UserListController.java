package webserver.controller;

import http.request.Request;
import http.response.Response;
import http.session.Session;
import model.User;
import model.UserService;
import webserver.support.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class UserListController extends HttpController {

    private UserListController() {
    }

    public static UserListController getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    protected void doGet(Request request, Response response) throws IOException, URISyntaxException {
        Session session = request.getSession();

        if ("true".equals(session.getAttribute("logined"))) {
            List<User> users = UserService.getInstance().findAll();
            ModelAndView modelAndView = new ModelAndView("user/list");
            modelAndView.setModels("users", users);
            response.forward(modelAndView);
            return;
        }

        ModelAndView modelAndView = new ModelAndView("/user/login.html");
        response.forward(modelAndView);
    }

    private static class LazyHolder {
        private static final UserListController INSTANCE = new UserListController();
    }
}
