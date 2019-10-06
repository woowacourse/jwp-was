package http.controller;

import db.DataBase;
import http.model.Cookie;
import http.model.HttpRequest;
import http.model.HttpResponse;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.net.HttpHeaders.COOKIE;

public class ListController implements Controller {
    private static final String JSESSIONID = "JSESSIONID";

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        Cookie cookie = new Cookie(httpRequest.getHeader(COOKIE));

        if (cookie.hasCookie(JSESSIONID)) {
            List<User> users = new ArrayList<>(DataBase.findAll());
            Map<String, Object> model = new HashMap<>();
            model.put("users", users);

            return new HttpResponse.Builder()
                    .forwardByTemplate("user/list", model)
                    .build();
        }

        return new HttpResponse.Builder()
                .sendRedirect("/user/login.html")
                .build();
    }
}
