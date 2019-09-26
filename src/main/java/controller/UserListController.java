package controller;

import db.DataBase;
import model.User;
import webserver.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends AbstractController {

    private static final String USER_LIST_URL = "/user/list";
    private static final String LOGINED_COOKIE_KEY = "logined";

    public UserListController() {
        methods = new HashMap<>();
        methods.put(new RequestMapping(HttpMethod.GET, USER_LIST_URL), this::doGet);
    }

    private Response doGet(Request request) {
        if (CookieLoginStatus.match(CookieLoginStatus.TRUE, request.getCookie(LOGINED_COOKIE_KEY))) {
            Map<String, Collection<User>> params = new HashMap<>();
            params.put("users", DataBase.findAll());

            return Response.ResponseBuilder.forward("/user/list", params)
                    .build();
        }

        return Response.ResponseBuilder.redirect("/user/login.html")
                .build();
    }
}
