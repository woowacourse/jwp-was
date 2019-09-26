package webserver.controller;

import http.request.Request;
import http.response.Response;
import model.User;
import model.UserService;
import webserver.exception.InvalidRequestMethodException;
import webserver.support.CookieParser;
import webserver.support.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class UserListController extends HttpController {

    private UserListController() {
    }

    public static UserListController getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    protected void doGet(Request request, Response response) throws IOException, URISyntaxException {
        ModelAndView modelAndView;
        try {
            Map<String, String> cookies = CookieParser.parse(request.extractHeader("Cookie"));
            if (!"true".equals(cookies.get("logined"))) {
                throw new IllegalArgumentException();
            }
            List<User> users = new UserService().findAll();
            modelAndView = new ModelAndView("user/list");
            modelAndView.setModels("users", users);
        } catch (RuntimeException e) {
            modelAndView = new ModelAndView("/user/login.html");
        }
        response.configureOkResponse(modelAndView);
    }

    @Override
    protected void doPost(Request request, Response response) {
        throw new InvalidRequestMethodException();
    }

    private static class LazyHolder {
        private static final UserListController INSTANCE = new UserListController();
    }
}
