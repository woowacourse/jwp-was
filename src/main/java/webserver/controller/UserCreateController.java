package webserver.controller;

import db.DataBase;
import model.User;
import webserver.Controller;
import webserver.HttpRequest;

public class UserCreateController implements Controller {
    public static final String PATH = "/user/create";
    private static final UserCreateController INSTANCE = new UserCreateController();

    public static UserCreateController getInstance() {
        return INSTANCE;
    }

    @Override
    public String service(HttpRequest request) {
        if (request.getMethod().equals("POST")) {
            return doPost(request);
        }
        throw new IllegalArgumentException("지원하지 않는 Http Method 입니다.");
    }

    private String doPost(HttpRequest request) {
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
