package user.controller;

import user.db.DataBase;
import user.model.User;
import utils.ModelMapper;
import webserver.controller.Controller;
import webserver.http.request.HttpParams;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.servlet.AbstractView;
import webserver.servlet.RedirectView;

public class UserCreateController implements Controller {
    @Override
    public AbstractView doService(HttpRequest request, HttpResponse response) {
        HttpParams bodyParams = HttpParams.of(request.getBody());

        User user = ModelMapper.toModel(User.class, bodyParams);
        DataBase.addUser(user);

        return new RedirectView("/index.html");
    }
}