package controller;

import webserver.Request;
import webserver.Response;

public class UserListController extends AbstractController {

    private static final String USER_LIST_URL = "/user/list";
    private static final String LOGINED_COOKIE_KEY = "logined";

    @Override
    public Response doGet(Request request) {
        String cookie = request.getCookie(LOGINED_COOKIE_KEY);
        if ("true".equals(cookie)) {
            return Response.ResponseBuilder.forward("/user/list")
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
