package webserver;

import db.DataBase;
import model.User;

public class UserController {
    public static UserController getInstance() {
        return UserControllerLazyHolder.INSTANCE;
    }

    public String create(RequestParameter requestParameter) {
        String userId = requestParameter.getParameter("userId");
        String password = requestParameter.getParameter("password");
        String name = requestParameter.getParameter("name");
        String email = requestParameter.getParameter("email");

        User user = new User(userId, password, name, email);

        DataBase.addUser(user);

        return "index";
    }

    private static class UserControllerLazyHolder {
        private static final UserController INSTANCE = new UserController();
    }
}
