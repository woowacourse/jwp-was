package webserver.controller;

import http.request.Request;
import http.response.Response;
import http.session.Session;
import http.session.Sessions;
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
        Map<String, String> cookies = CookieParser.parse(request.extractHeader("Cookie"));
        Session session = Sessions.getInstance().getSession(cookies.get("JSESSIONID"));

        if ("true".equals(session.getSessionAttribute("logined"))) {
            List<User> users = new UserService().findAll();
            modelAndView = new ModelAndView("user/list");
            modelAndView.setModels("users", users);
            response.forward(modelAndView);
            return;
        }

        modelAndView = new ModelAndView("/user/login.html");
        response.forward(modelAndView);
    }

    @Override
    protected void doPost(Request request, Response response) {
        throw new InvalidRequestMethodException();
    }

    private static class LazyHolder {
        private static final UserListController INSTANCE = new UserListController();
    }
}
