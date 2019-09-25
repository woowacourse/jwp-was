package webserver.controller;

import http.request.Request;
import http.response.Response;
import model.User;
import model.UserService;
import webserver.exception.InvalidRequestMethodException;
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
        List<User> users = new UserService().findAll();
        ModelAndView modelAndView = new ModelAndView("/user/list.html");
        modelAndView.setModels("users", users);
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
