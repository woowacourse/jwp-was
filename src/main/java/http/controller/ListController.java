package http.controller;

import db.DataBase;
import http.model.HttpRequest;
import http.model.HttpResponse;
import http.model.HttpSession;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListController implements Controller {
    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        HttpSession httpSession = httpRequest.getHttpSession();

        if (httpSession.getAttribute("user") != null) {
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
