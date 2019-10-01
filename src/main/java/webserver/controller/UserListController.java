package webserver.controller;

import db.DataBase;
import model.User;
import webserver.View;
import webserver.http.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import static webserver.controller.LoginController.LOGINED;

public class UserListController extends AbstractController {
    public static final String PATH = "/user/list";

    @Override
    protected View doGet(HttpRequest request) {
        if (isNotLoggedIn(request)) {
            return new View(REDIRECT_VIEW + "/user/login.html");
        }
        List<User> users = new ArrayList<>(DataBase.findAll());

        //TODO MODEL AND VIEW
        return new View("/user/list.html", "user", users);
    }

    private boolean isNotLoggedIn(HttpRequest httpRequest) {
        return !httpRequest.checkSessionAttribute(LOGINED, "true");
    }
}
