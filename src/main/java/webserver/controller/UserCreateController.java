package webserver.controller;

import db.DataBase;
import model.User;
import webserver.HttpRequest;
import webserver.exception.NotSupportedHttpMethodException;

public class UserCreateController extends AbstractController {
    public static final String PATH = "/user/create";
    private static final UserCreateController INSTANCE = new UserCreateController();

    public static UserCreateController getInstance() {
        return INSTANCE;
    }

    @Override
    protected String doPost(HttpRequest request) {
        User user = User.Builder.anUser()
                .userId(request.getParam("userId"))
                .email(request.getParam("email"))
                .name(request.getParam("name"))
                .password(request.getParam("password"))
                .build();

        DataBase.addUser(user);

        return "/redirect:/index.html";
    }
}
