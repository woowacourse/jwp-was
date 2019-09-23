package controller;

import db.DataBase;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends AbstractController {

    private static final String USER_LIST_URL = "/user/list";
    private static final String LOGINED_ATTR_KEY = "logined";

    @Override
    public void doGet(HttpRequest req, HttpResponse res) {
        if ("true".equals(req.getSession().getAttribute(LOGINED_ATTR_KEY))) {
            Map<String, Collection<User>> params = new HashMap<>();
            params.put("users", DataBase.findAll());

            res.forward("user/list", params);
            return;
        }

        res.redirect("/user/login.html");
    }

    @Override
    public void doPost(HttpRequest req, HttpResponse res) {
        throw createUnsupportedException();
    }

    @Override
    public String getPath() {
        return USER_LIST_URL;
    }
}
