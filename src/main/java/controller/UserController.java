package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import static view.RedirectViewMatcher.REDIRECT_SIGNATURE;

public class UserController extends AbstractController {
    public static UserController getInstance() {
        return UserControllerLazyHolder.INSTANCE;
    }

    @Override
    public ModelAndView doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getFormDataParameter("userId");
        String password = httpRequest.getFormDataParameter("password");
        String name = httpRequest.getFormDataParameter("name");
        String email = httpRequest.getFormDataParameter("email");

        User user = new User(userId, password, name, email);

        DataBase.addUser(user);

        return new ModelAndView(String.format("%s%s", REDIRECT_SIGNATURE, "/"));
    }

    private static class UserControllerLazyHolder {
        private static final UserController INSTANCE = new UserController();
    }
}
