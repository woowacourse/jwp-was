package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static webserver.controller.LoginController.LOGINED;

public class UserListController extends AbstractController {

    public static final String PATH = "/user/list";
    private static final UserListController INSTANCE = new UserListController();

    public static UserListController getInstance() {
        return INSTANCE;
    }

    @Override
    protected String doGet(HttpRequest request, HttpResponse response) {
        if (isNotLoggedIn(request, response)) {
            return REDIRECT_VIEW + "/user/login.html";
        }
        List<User> users = new ArrayList<>(DataBase.findAll());
        response.addModel("user", users);

        return "/user/list.html";
    }

    private boolean isNotLoggedIn(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpSession session = getSession(httpRequest, httpResponse);
        return !session.checkAttribute(LOGINED, "true");
    }
}
