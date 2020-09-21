package slipp.web;

import java.util.Objects;

import slipp.db.DataBase;
import slipp.model.User;
import webserver.controller.AbstractController;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

public class UserController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        String name = httpRequest.getParameter("name");
        String email = httpRequest.getParameter("email");

        if (isEmpty(userId, password, name, email)) {
            httpResponse.sendError(HttpStatus.BAD_REQUEST, "유저정보가 존재하지 않습니다.");
            return;
        }

        DataBase.addUser(new User(userId, password, name, email));

        httpResponse.sendRedirect("/index.html");
    }

    private boolean isEmpty(String userId, String password, String name, String email) {
        return Objects.isNull(userId) || Objects.isNull(password) || Objects.isNull(name) || Objects.isNull(email);
    }
}
