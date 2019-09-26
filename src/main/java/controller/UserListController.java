package controller;

import db.DataBase;
import model.User;
import webserver.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserListController implements Controller {

    private static final String USER_LIST_URL = "/user/list";
    private static final String LOGINED_COOKIE_KEY = "logined";

    private static Map<RequestMapping, HandleHttpMethod> methods;

    static {
        methods = new HashMap<>();
        methods.put(new RequestMapping(HttpMethod.GET, USER_LIST_URL), HandleHttpMethod.GET);
    }

    @Override
    public Response service(Request request) {
        return methods.get(request.getRequestMapping()).method(request);
    }

    @Override
    public Set<RequestMapping> getMethodKeys() {
        return methods.keySet();
    }

    @Override
    public boolean isMapping(Request request) {
        return methods.containsKey(request.getRequestMapping());
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
