package webserver.controller;

import db.DataBase;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class UserListController extends AbstractController {

    public static final String PATH = "/user/list";
    private static final UserListController INSTANCE = new UserListController();

    public static UserListController getInstance() {
        return INSTANCE;
    }


    @Override
    protected String doGet(HttpRequest request, HttpResponse response) {
        String cookie = request.getCookie();
        if (validateNotLoggedIn(cookie)) {
            return "/redirect:/user/login.html";
        }
        List<User> users = new ArrayList<>(DataBase.findAll());
        response.addModel("user", users);

        return "/user/list.html";
    }

    private boolean validateNotLoggedIn(String cookie) {
        boolean contains = cookie.contains("logined=true");
        return !contains;
    }
}
