package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class UserController extends AbstractController {
    public static UserController getInstance() {
        return UserControllerLazyHolder.INSTANCE;
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getFormDataParameter("userId");
        String password = httpRequest.getFormDataParameter("password");
        String name = httpRequest.getFormDataParameter("name");
        String email = httpRequest.getFormDataParameter("email");

        User user = new User(userId, password, name, email);

        DataBase.addUser(user);

        handle(new ModelAndView("redirect: /"), httpResponse);
    }

    private static class UserControllerLazyHolder {
        private static final UserController INSTANCE = new UserController();
    }
}
