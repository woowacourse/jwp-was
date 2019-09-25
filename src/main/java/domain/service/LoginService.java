package domain.service;

import domain.db.UserDataBase;
import domain.model.User;

public class LoginService {
    public boolean validate(String id, String password) {
        User user = UserDataBase.findUserById(id);
        return user != null && user.getPassword().equals(password);
    }

    public static LoginService getInstance() {
        return LoginService.LazyHolder.INSTANCE;
    }

    public static class LazyHolder {
        private static final LoginService INSTANCE = new LoginService();
    }
}
