package web.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import db.DataBase;
import model.User;
import view.ModelAndView;
import view.View;
import web.StaticFile;
import web.http.HttpRequest;
import web.http.HttpResponse;
import web.http.HttpStatus;

public class UserListController extends AbstractController {
    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        response.addHttpStatus(HttpStatus.NOT_ALLOWED_METHOD);
        ExceptionHandler.processException(new NoSuchMethodException("지원하지 않는 메서드입니다."), response);
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        if (isNotLoggedIn(request)) {
            response.redirect("/user/login");
            return;
        }

        Collection<User> users = DataBase.findAll();
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        String location = "/user/list.html";
        View view = new View(StaticFile.of(location).getPrefix() + location);
        response.ok(new ModelAndView(view, model));
    }

    private boolean isNotLoggedIn(HttpRequest request) {
        return request.getHeaders("Cookie") == null;
    }
}
