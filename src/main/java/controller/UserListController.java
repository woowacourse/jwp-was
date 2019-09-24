package controller;

import db.DataBase;
import model.User;
import webserver.CookieLoginStatus;
import webserver.Request;
import webserver.Response;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends AbstractController {

    private static final String USER_LIST_URL = "/user/list";
    private static final String LOGINED_COOKIE_KEY = "logined";

    @Override
    public Response doGet(Request request) {
        if (CookieLoginStatus.match(CookieLoginStatus.TRUE, request.getCookie(LOGINED_COOKIE_KEY))) {
            Map<String, Collection<User>> params = new HashMap<>();
            params.put("users", DataBase.findAll());
            return Response.ResponseBuilder.forward("/user/list", params)
                    .build();
        }

        return Response.ResponseBuilder.redirect("/user/login.html")
                .build();
    }

    @Override
    public Response doPost(Request request) {
        throw createUnsupportedException();
    }

    @Override
    public String getPath() {
        return USER_LIST_URL;
    }
}
