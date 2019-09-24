package controller;

import db.DataBase;
import model.User;
import webserver.CookieLoginStatus;
import webserver.HttpMethod;
import webserver.Request;
import webserver.Response;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController implements Controller {

    private static final String USER_LIST_URL = "/user/list";
    private static final String LOGINED_COOKIE_KEY = "logined";

    private static Map<HttpMethod, HandleHttpMethod> methods;

    static {
        methods = new HashMap<>();
        methods.put(HttpMethod.GET, HandleHttpMethod.GET);
    }

    @Override
    public Response service(Request request) {
        return methods.get(request.getMethod()).method(request);
    }

    @Override
    public String getPath() {
        return USER_LIST_URL;
    }

    private enum HandleHttpMethod {
        GET {
            Response method(Request request) {
                if (CookieLoginStatus.match(CookieLoginStatus.TRUE, request.getCookie(LOGINED_COOKIE_KEY))) {
                    Map<String, Collection<User>> params = new HashMap<>();
                    params.put("users", DataBase.findAll());

                    return Response.ResponseBuilder.forward("/user/list", params)
                            .build();
                }

                return Response.ResponseBuilder.redirect("/user/login.html")
                        .build();
            }
        };

        abstract Response method(Request request);
    }
}
